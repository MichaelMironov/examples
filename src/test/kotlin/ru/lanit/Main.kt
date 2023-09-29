package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class Main {

    private lateinit var driver: WebDriver

    @BeforeEach
    fun setup() {
//        driver = RemoteWebDriver.builder()
//            .oneOf(SafariOptions())
//            .addAlternative(ChromeOptions()).build()
        driver = WebDriverManager.chromedriver().create()
    }

    @Test
    fun openSite() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html")

        val element = driver.findElement(By.tagName("textarea"))

        assertThat(element.getDomAttribute("rows")).isEqualTo("3")
    }
}