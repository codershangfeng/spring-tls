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

1. Configure TLS in Spring 
   Paste these configurations in `application.properties` file:
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

1. Validate TLS enabled from Chrome
   Open a new **incognito** window, and access to `https://localhost/greeting?userName=sam`. By default, Chrome will complaint with unsecure error message. To bypass it, just type text `thisisunsafe` while browsing the error web page, then the browser will redirect to the target endpoint.


## How to configure mTLS in Spring Boot

TODO

## Reference:
1. https://www.baeldung.com/spring-tls-setup
