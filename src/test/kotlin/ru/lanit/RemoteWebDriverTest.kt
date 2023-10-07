package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager.isOnline
import org.assertj.core.api.Assumptions.assumeThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class RemoteWebDriverTest : BaseTest() {

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        val seleniumServerUrl = URL("http://localhost:4444/wd/hub")
        assumeThat(isOnline(seleniumServerUrl)).isTrue

        val options = ChromeOptions()
        val selenoidOptions = HashMap<String, Any>()

        selenoidOptions["enableVNC"] = true
        selenoidOptions["name"] = testInfo.displayName
        options.setCapability("selenoid:options", selenoidOptions)

        driver = RemoteWebDriver(seleniumServerUrl, options)
    }

    @Test
    @DisplayName("[WEB] Selenoid test")
    fun testGrid() {
        driver.get(BASE_URI)
        println()
    }
}