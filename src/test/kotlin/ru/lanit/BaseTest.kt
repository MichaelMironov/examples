package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

const val BASE_URI = "https://bonigarcia.dev/selenium-webdriver-java/"

open class BaseTest {

    var driver: WebDriver = WebDriverManager.chromedriver().create()
    val wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(10L))
    val actions = Actions(driver)
    val js: JavascriptExecutor = driver as JavascriptExecutor

    init {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L))
    }
}