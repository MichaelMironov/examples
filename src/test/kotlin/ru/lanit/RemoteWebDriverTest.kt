package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager.isOnline
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class RemoteWebDriverTest : BaseTest() {

    @BeforeEach
    fun setup() {
        val seleniumServerUrl = URL("http://localhost:4444/")
        assumeThat(isOnline(seleniumServerUrl)).isTrue

        val options = ChromeOptions()
        driver = RemoteWebDriver(seleniumServerUrl, options)
    }
}