package com.example.springtls.primary.adapter

import com.example.springtls.primary.port.AppService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GreetingControllerTest {

    private val appService: AppService = mock()
    private lateinit var greetingController: GreetingController

    @BeforeEach
    internal fun setUp() {
        greetingController = GreetingController(appService = appService)
    }

    @Test
    internal fun `should return greeting successfully`() {
        val got = greetingController.greeting("sam")

        verify(appService).greeting()
        assertNotNull(got.block())
        assertEquals("hey, there! sam", got.block()?.message)
    }
}