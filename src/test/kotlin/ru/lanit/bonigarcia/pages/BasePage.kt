package ru.lanit.bonigarcia.pages

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

const val BASE_URI = "https://bonigarcia.dev/selenium-webdriver-java/"

abstract class BasePage(protected val driver: WebDriver) {

    private var timeout = 10L

    protected val wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(timeout))

    fun setTimeout(sec: Long) {
        this.timeout = sec
    }

    fun visit(url: String) {
        driver.get(BASE_URI + url)
    }

    fun find(locator: By): WebElement? {
        return driver.findElement(locator)
    }

    fun click(locator: By) {
        find(locator)!!.click()
    }

    fun type(element: By, text: String) {
        find(element)!!.sendKeys(text)
    }

    fun isDisplayed(locator: By): Boolean {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
        } catch (e: TimeoutException) {
            println("Timeout of $timeout wait for $locator")
            return false
        }
        return true
    }
}