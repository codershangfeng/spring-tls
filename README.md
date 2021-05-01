# Spring-TLS

## Description

A demo project for showing how to enable (mutual) TLS in Spring Boot (WebFlux).

## How to configure TLS in Spring Boot

1. Generate a Key Pair
    ```bash
    $ keytool -genkeypair -alias baeldung -keyalg RSA -kesize 4096 \
        -validity 3650 -dname "CN=localhost" -keypass changeit -keystore keystore.p12 \
        -storeType PKCS12 -storepass changeit
    ```
   Output:
    ```plantext
    Generating 4,096 bit RSA key pair and self-signed certificate (SHA384withRSA) with a validity of 3,650 days
        for: CN=localhost
    ```
   To view its details, run:
   ```bash
   $ keytool -v -list -keystore keystore.p12
   ```
   Sample result as below:
   ```plaintext
   Keystore type: PKCS12
   Keystore provider: SUN
   
   Your keystore contains 1 entry
   
   Alias name: baeldung
   Creation date: Apr 30, 2021
   Entry type: PrivateKeyEntry
   Certificate chain length: 1
   Certificate[1]:
   Owner: CN=localhost
   Issuer: CN=localhost
   Serial number: XXXXXXXXXXXXXXXX
   Valid from: Fri Apr 30 19:53:55 CST 2021 until: Mon Apr 28 19:53:55 CST 2031
   Certificate fingerprints:
   SHA1: {SHA1_FINGERPRINTS}
   SHA256: {SHA256_FINGERPRINTS}
   Signature algorithm name: SHA384withRSA
   Subject Public Key Algorithm: 4096-bit RSA key
   Version: 3
   
   Extensions:
   
   #1: ObjectId: 2.5.29.14 Criticality=false
   SubjectKeyIdentifier [
   KeyIdentifier [
   0000: {BLA_BLA_BLA}
   0010: {BLA_BLA_BLA}
   ]
   ]
   
   *******************************************
   *******************************************
   ```

1. Configure TLS in Spring

   Put these configurations in `application.properties` file:
   ```properties
    # enable/disable https
    server.ssl.enabled=true
    # keystore format
    server.ssl.key-store-type=PKCS12
    # keystore location
    server.ssl.key-store=classpath:keystore/keystore.p12
    # keystore password
    server.ssl.key-store-password=changeit
    # SSL protocol to use
    server.ssl.protocol=TLS
    # Enabled SSL protocols
    server.ssl.enabled-protocols=TLSv1.2
    ```

1. Paste generated `.p12` file into `./target/classes/keystore` folder after runs `mvn clean install`.

1. Validate TLS enabled

    1. Chrome

       Open a new **incognito** window, and access to `https://localhost/greeting?userName=sam`. By default, Chrome will
       complaint with unsecure error message. To bypass it, just type text `thisisunsafe` while browsing the error web
       page, then the browser will redirect to the target endpoint.

    1. Postman

       By default, it throws an error message `SSL Error: Self signed certificate`, because we are really using a
       self-signed certificate as step 1. To bypass it temporarily, just disable ssl verification.

## How to configure mTLS in Spring Boot

For enabling mTLS, we use the *client-auth* attribute with the *need* value:

```properties
server.ssl.client-auth=need
```

When we use the *need* value, client authentication is needed and mandatory. This means that both the client and server
must share their public certificate. For storing the client's certificate in the Spring Boot application. we use the *
truststore* file and configure it in the `application.properties` file:

```properties
#trust store location
server.ssl.trust-store=classpath:keystore/truststore.p12
#trust store password
server.ssl.trust-store-password=changeit
```

## How to invoke an HTTPS API

1. [Export the Public Key Certificate](https://docs.oracle.com/javase/tutorial/security/toolsign/step5.html)

   As authenticate server's signature, client needs to have the public key corresponding to the private key used to
   generate the signature.

   ```bash
   $ keytool -export -keystore keystore.p12 -alias baeldung -file baeldung.cer
   ```
   **NOTE**: The value for the flag `-alias` should be same as the one in the step 1. Otherwise, an exception throws.

   To view this generated certificate, use `keytool`:
   ```bash
   $ keytool -printcert -v -file baeldung.cer
   ```

1. cURL
   ```bash
   $ curl --cacert baeldung.cer  https://localhost:8080/greeting\?username\=sam -v --insecure
   ```
   **NOTE**: Many HTTP clients have a check on the Self-Signed certificates, and it does not allow accessing resources
   over Self-Signed certificates HTTPS. A workaround solution is adding `--insecure` flag in the cURL script above.

## Reference:

1. https://www.baeldung.com/spring-tls-setup
2. https://medium.com/swlh/how-to-secure-a-spring-boot-application-with-tls-176062895559
3. https://www.sslshopper.com/article-most-common-java-keytool-keystore-commands.html
