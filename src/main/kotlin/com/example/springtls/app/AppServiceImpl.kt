package com.example.springtls.app

import com.example.springtls.primary.port.AppService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AppServiceImpl: AppService {
    companion object {
        private val logger = LoggerFactory.getLogger(AppServiceImpl::class.java)
    }

    override fun greeting() {
        logger.info("Hey, there!")
    }
}