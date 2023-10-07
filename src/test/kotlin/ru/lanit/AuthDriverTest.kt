package ru.lanit

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.HasAuthentication
import org.openqa.selenium.UsernameAndPassword
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions
import java.time.Duration
import java.util.*

class AuthDriverTest : BaseTest() {


    @Test
    fun testBasicAuth() {
        val authentication = driver as HasAuthentication
        authentication.register { UsernameAndPassword("guest", "guest") }

        driver.get("https://jigsaw.w3.org/HTTP/Basic/")

        val body = driver.findElement(By.tagName("body"))
        assertThat(body.text).contains("Your browser made it!")
    }

    @Test
    fun testWebAuth() {
        driver.get("https://webauthn.io/")
        val virtualAuthenticator = driver as HasVirtualAuthenticator

        val authenticator: VirtualAuthenticator = virtualAuthenticator.addVirtualAuthenticator(VirtualAuthenticatorOptions())

        val uuid = UUID.randomUUID().toString()

        driver.findElement(By.id("input-email")).sendKeys(uuid)
        val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(10L))

        driver.findElement(By.id("register-button")).click()
        webDriverWait.until(
            ExpectedConditions.textToBePresentInElementLocated(
                By.className("alert-success"),
                "Success! Now try to authenticate..."
            )
        )

        driver.findElement(By.id("login-button")).click()

        webDriverWait.until(
            ExpectedConditions.textToBePresentInElementLocated(
                By.className("main-content"),
                "You're logged in!"
            )
        )

        virtualAuthenticator.removeVirtualAuthenticator(authenticator)
    }
}