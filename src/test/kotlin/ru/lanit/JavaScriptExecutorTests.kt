package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.Color
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration.ofMillis
import java.time.Duration.ofSeconds
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class JavaScriptExecutorTests {

    val driver: WebDriver = WebDriverManager.chromedriver().create()
    val wait: WebDriverWait = WebDriverWait(driver, ofSeconds(10L))
    val actions = Actions(driver)
    val js: JavascriptExecutor = driver as JavascriptExecutor

    @Test
    fun testColor() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html")

        val javascriptExecutor = driver as JavascriptExecutor

        val colorPicker = driver.findElement(By.name("my-colors"))

        val originColor = colorPicker.getAttribute("value")

        println("Stated color is $originColor")

        val red = Color(255, 0, 0, 1.0)

        val script = "arguments[0].setAttribute('value', '$red');"

        javascriptExecutor.executeScript(script, colorPicker)

        val expectedColor = colorPicker.getAttribute("value")
        println("Color after change - $expectedColor")

        assertNotEquals(originColor, expectedColor)

        assertEquals(Color.fromString(expectedColor), red)
    }

    @Test
    fun testAsyncScript() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/")

        val pause = ofSeconds(2L)

        val script = """
            |const callback = arguments[arguments.length - 1];
            |window.setTimeout(callback, ${pause.toMillis()});
        """.trimMargin()

        val initMills = System.currentTimeMillis()
        js.executeAsyncScript(script)

        val elapsed = ofMillis(System.currentTimeMillis() - initMills)

        println("Script worked - ${elapsed.toMillis()} ms")

        assertTrue(elapsed > pause)

    }

    @Test
    fun testPageLoadTimeout() {
        driver.manage().timeouts().pageLoadTimeout(ofMillis(1L))


        assertThatThrownBy { driver.get("https://www.google.com/") }
            .isInstanceOf(TimeoutException::class.java)
    }

}