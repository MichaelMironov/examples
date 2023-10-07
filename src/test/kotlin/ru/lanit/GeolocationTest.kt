package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration

class GeolocationTest : BaseTest() {

    @BeforeEach
    fun setup() {
        val chromeOptions = ChromeOptions()
        val prefs = HashMap<String, Any>()
        prefs["profile.default_content_setting_values.geolocation"] = 1
        chromeOptions.setExperimentalOption("prefs", prefs)

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L))

        driver = WebDriverManager.chromedriver().capabilities(chromeOptions).create()
    }

    @Test
    fun testGeo() {
        driver.get(BASE_URI + "geolocation.html")
        driver.findElement(By.id("get-coordinates")).click()
        println()
    }

}