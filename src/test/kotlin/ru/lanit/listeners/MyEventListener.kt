package ru.lanit.listeners

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.WebDriverListener
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

class MyEventListener : WebDriverListener {

    override fun afterGet(driver: WebDriver?, url: String?) {
        super.afterGet(driver, url)
        takeScreenshot(driver)
    }

    override fun beforeQuit(driver: WebDriver?) {
        super.beforeQuit(driver)
        takeScreenshot(driver)
    }

    private fun takeScreenshot(driver: WebDriver?) {
        val takesScreenshot = driver as TakesScreenshot
        val screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE)

        val remoteWebDriver = driver as RemoteWebDriver
        val sessionId = remoteWebDriver.sessionId

        val today = Date()
        val format = SimpleDateFormat("yyyy.MM.dd_HH.mm.ss.SSS")

        var screenshotFileName = "${format.format(today)}-$sessionId.png"

        val destinationPath = Paths.get(screenshotFileName)
        Files.move(screenshot.toPath(), destinationPath)

    }
}