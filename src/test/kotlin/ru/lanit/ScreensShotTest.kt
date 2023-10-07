package ru.lanit

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import ru.lanit.condition.PhotoCondition

class ScreensShotTest : BaseTest() {

    @Test
    fun testWebElementScreenshot() {
        driver.get(BASE_URI + "web-form.html")
        val form = driver.findElement(By.tagName("form"))
        val screenshot = form.getScreenshotAs(OutputType.BASE64)

        println(
            """
            Screenshot in base64:
            data:image/png; base64, $screenshot
        """.trimIndent()
        )

        assertThat(screenshot).asBase64Decoded()
    }

    @Test
    fun testScreensShotBASE64() {
        driver.get(BASE_URI)
        val takesScreenshot = driver as TakesScreenshot

        val screenshot = takesScreenshot.getScreenshotAs(OutputType.BASE64)
        println(
            """Screenshot in base64:
            |data:image/png;base64, $screenshot
            """.trimMargin()
        )

        assertThat(screenshot).isBase64
        assertThat(screenshot).isNotEmpty
    }

    @Test
    fun testWindow() {
        driver.get(BASE_URI)

        val window = driver.manage().window()

        println("Start position = ${window.position}. Start Size = ${window.size}")

        wait.until(PhotoCondition(expectedPhoto = "", actualPhoto = ""))

        window.maximize()

        println("After maximize:\nPosition = ${window.position}. Size = ${window.size}")

    }
}