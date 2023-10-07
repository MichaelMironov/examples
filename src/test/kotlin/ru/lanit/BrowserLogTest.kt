package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.PrintsPage
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.print.PrintOptions
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.logging.Level

class BrowserLogTest : BaseTest() {

    @BeforeEach
    fun setup() {
        val logs = LoggingPreferences()
        logs.enable(LogType.BROWSER, Level.ALL)

        val options = ChromeOptions()
        options.setCapability("goog:loggingPrefs", logs)

        driver = WebDriverManager.chromedriver().capabilities(options).create()
    }

    @Test
    fun testBrowserLogs() {
        driver.get(BASE_URI + "console-logs.html")
        val logEntries = driver.manage().logs().get(LogType.BROWSER)

        assertThat(logEntries.all).isNotEmpty
        logEntries.forEach { println(it) }
    }

    @Test
    fun testPdfPrintPage() {
        driver.get(BASE_URI)

        val printsPage = driver as PrintsPage
        val printOptions = PrintOptions()
        val pdfPage = printsPage.print(printOptions)

        val pdfBase64 = pdfPage.content
        assertThat(pdfBase64).contains("JVBER")

        val decodedImg = Base64.getDecoder().decode(pdfBase64.toByteArray(UTF_8))

        val path = Paths.get("my-pdf.pdf")
        Files.write(path, decodedImg)

    }
}