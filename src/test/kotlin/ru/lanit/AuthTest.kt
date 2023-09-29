package ru.lanit

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import ru.lanit.config.Browser
import ru.lanit.config.getWebDriver
import ru.lanit.pages.LoginPage


class AuthTest {

    private val driver: WebDriver = getWebDriver(Browser.EDGE)

    @Test
    fun loginTest() {

        LoginPage(driver)
            .open()
            .waitPageLoaded()
            .loginAs("admin", "adminat")
            .userShouldHaveUsername("admin")

    }

    @AfterEach
    fun closeDriver() {
        driver.close()
    }
}