package ru.lanit.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

const val DEFAULT_TIME_OUT = 10L

abstract class BasePage<T : BasePage<T>>(
    protected val driver: WebDriver = ChromeDriver(),
    protected val wait: WebDriverWait = WebDriverWait(
        driver,
        Duration.ofSeconds(DEFAULT_TIME_OUT)
    )
) {

    init {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIME_OUT))
    }

    protected fun clickAndWaitPage(locator: By, page: T): T {
        wait.until(elementToBeClickable(locator)).click()
        return page
    }

    protected fun click(locator: By) {
        wait.until(elementToBeClickable(locator)).click()
    }

    protected fun input(text: String, to: By) {
        wait.until(visibilityOfElementLocated(to)).sendKeys(text)
    }

    protected fun waitVisibility(locator: By): WebElement {
        return wait.until(visibilityOfElementLocated(locator))
    }

    protected fun switchToNextTab() {
        driver.switchTo().window(
            driver.windowHandles.filterNot { it.equals(driver.windowHandle) }.first()
        )
    }

    abstract fun waitPageLoaded(): T
}


