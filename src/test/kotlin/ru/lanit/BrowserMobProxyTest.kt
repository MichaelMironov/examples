package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class BrowserMobProxyTest {

//    lateinit var driver1: WebDriver
//    lateinit var proxy: BrowserMobProxy
//
//    @BeforeEach
//    fun setup() {
//        proxy = BrowserMobProxyServer()
//        proxy.start()
//        proxy.newHar()
//        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT)
//
//        val seleniumProxy = ClientUtil.createSeleniumProxy(proxy)
//        val options = ChromeOptions()
//        options.setProxy(seleniumProxy)
//        options.setAcceptInsecureCerts(true)
//
//        driver1 = WebDriverManager.chromedriver().capabilities(options).create()
//
//    }
//
//    @AfterEach
//    fun tearDown() {
//        proxy.stop()
//        driver1.quit()
//    }
//
//    @Test
//    fun testCaptureNetworkTraffic() {
//        driver1.get(BASE_URI)
//        proxy.har.log.entries.forEach { println(it.request.url + " - " + it.response.status) }
//    }
}