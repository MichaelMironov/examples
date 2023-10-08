package ru.lanit.bonigarcia.tests

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import ru.lanit.bonigarcia.pages.LoginPage

class LoginTest {
    lateinit var driver: WebDriver
    lateinit var login: LoginPage

    @BeforeEach
    fun setup() {
        driver = WebDriverManager.chromedriver().create()
        login = LoginPage(driver)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun testLoginSuccess() {
        login.with("user", "user")
        assertThat(login.successLoginMessagePresent()).isTrue
    }
}