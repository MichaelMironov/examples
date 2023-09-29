package ru.lanit.config

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.safari.SafariDriver
import ru.lanit.config.Browser.*

val config: TestConfigFactory = getInstance()
fun getWebDriver(browser: Browser): WebDriver {
    return when (browser) {
        FIREFOX -> FirefoxDriver()
        SAFARI -> SafariDriver()
        IE -> InternetExplorerDriver()
        EDGE -> EdgeDriver()
        else -> ChromeDriver()
    }
}

enum class Browser {
    EDGE, IE, SAFARI, FIREFOX
}