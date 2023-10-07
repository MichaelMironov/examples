package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.Proxy
import org.openqa.selenium.chrome.ChromeOptions

class ProxyTest : BaseTest() {


    @BeforeEach
    fun setup() {
        val proxy = Proxy()
        val proxyPort = "proxy:port"
        proxy.httpProxy = proxyPort
        proxy.sslProxy = proxyPort

        val chromeOptions = ChromeOptions()
        chromeOptions.setAcceptInsecureCerts(true)

        chromeOptions.setProxy(proxy)

        driver = WebDriverManager.chromedriver().capabilities(chromeOptions).create()
    }

    @Test
    fun testProxy() {
        driver.get(BASE_URI)
        println()
    }
}