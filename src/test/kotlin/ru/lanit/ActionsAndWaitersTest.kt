package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.apache.commons.lang3.SystemUtils
import org.junit.jupiter.api.Test
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ActionsAndWaitersTest {

    val driver: WebDriver = WebDriverManager.chromedriver().create()
    val wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(10L))
    val actions = Actions(driver)

    @Test
    fun testContextClick() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html")

        val dropdown = driver.findElement(By.id("my-dropdown-2"))

        val actions = Actions(driver)

        actions.contextClick(dropdown).build().perform()

        val contextMenu = driver.findElement(By.id("context-menu-2"))

        assertTrue(contextMenu.isDisplayed)
    }

    @Test
    fun testClickAndHold() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html")
        val actions = Actions(driver)

        val canvas = driver.findElement(By.tagName("canvas"))
        actions.moveToElement(canvas).clickAndHold()

        val numPoints = 10
        val radius = 30

        for (i in 0 until numPoints) {
            val angle: Double = Math.toRadians((360 * i / numPoints.toDouble()))

            val x = sin(angle) * radius
            val y = cos(angle) * radius

            actions.moveByOffset(x.toInt(), y.toInt())


        }
        actions.release(canvas).build().perform()
    }

    @Test
    fun copyAndPaste() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        val inputText = driver.findElement(By.name("my-text"))
        val textArea = driver.findElement(By.name("my-textarea"))

        val modifier: Keys = if (SystemUtils.IS_OS_MAC) Keys.COMMAND else Keys.CONTROL

        actions.sendKeys(inputText, "hello kotlin").keyDown(modifier)
            .sendKeys(inputText, "a", "c")
            .sendKeys(textArea, "v").build().perform()

        assertEquals(inputText.getAttribute("value"), textArea.getAttribute("value"))
    }

    @Test
    fun testFluentWait() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html")

        val fluentWait = FluentWait(driver)
            .withTimeout(Duration.ofSeconds(15L))
            .pollingEvery(Duration.ofSeconds(1L))
            .ignoring(NoSuchElementException::class.java)

        val landscape = fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")))

        assertTrue(landscape.getAttribute("src").contains("landscape", true))
    }

    @Test
    fun testScrollIntoView() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html")

        val javascriptExecutor = driver as JavascriptExecutor

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L))

        val lastElement = driver.findElement(By.cssSelector("p:last-child"))

        val script = "arguments[0].scrollIntoView();"

        javascriptExecutor.executeScript(script, lastElement)

    }


}