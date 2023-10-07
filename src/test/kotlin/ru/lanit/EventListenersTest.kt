package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.events.EventFiringDecorator
import ru.lanit.listeners.MyEventListener

class EventListenersTest : BaseTest() {

    lateinit var driver2: WebDriver

    @BeforeEach
    fun setUp() {
        val listener = MyEventListener()
        val originalDriver = WebDriverManager.chromedriver().create()
        driver2 = EventFiringDecorator<WebDriver>(listener).decorate(originalDriver)
    }

    @AfterEach
    fun tearDown() {
        driver2.quit()
    }

    @Test
    fun testEventListener() {
        driver2.get(BASE_URI)
        driver2.findElement(By.linkText("Web form")).click()
    }

    @Test
    fun testCapability() {
        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("--headless=new")
        driver = ChromeDriver(chromeOptions)
    }
}