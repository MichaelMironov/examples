package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.devtools.DevTools
import org.openqa.selenium.devtools.events.CdpEventTypes
import org.openqa.selenium.devtools.events.CdpEventTypes.*
import org.openqa.selenium.devtools.events.DomMutationEvent
import org.openqa.selenium.devtools.v114.network.Network
import org.openqa.selenium.devtools.v114.page.Page
import org.openqa.selenium.devtools.v114.page.model.Viewport
import org.openqa.selenium.devtools.v114.security.Security
import org.openqa.selenium.logging.HasLogEvents
import org.openqa.selenium.support.Color
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*
import java.util.concurrent.atomic.AtomicReference

class DevToolsTest {

    lateinit var webDriver: WebDriver
    lateinit var devTools: DevTools

    @BeforeEach
    fun setupDevtools() {
        webDriver = WebDriverManager.chromedriver().create()
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L))
        devTools = (webDriver as ChromeDriver).devTools
        devTools.createSession()
    }

    @AfterEach
    fun tearDown() {
        devTools.close()
        webDriver.quit()
    }

    @Test
    fun testNetworkMonitoring() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()))

        devTools.addListener(Network.requestWillBeSent()) {
            println(
                """
                Request: ${it.requestId}
                Method: ${it.request.method}
                URL: ${it.request.url}
                Headers: ${it.request.headers}
                """.trimIndent()
            )
        }

        devTools.addListener(Network.responseReceived()) {
            println(
                """
                Response: ${it.requestId}
                URL: ${it.response.url}
                Status: ${it.response.status}
                Response: ${it.response}
                """.trimIndent()
            )
        }

        webDriver.get("https://kotlinlang.ru/")

        webDriver.findElement(By.xpath("//*[@class='text' and contains(text(), 'Основы')]")).click()
        webDriver.findElement(By.xpath("//a[contains(text(), 'Рефлексия')]")).click()


        webDriver
            .findElement(By.xpath("//p[contains(text(), 'В Kotlin функции и свойства первичны')]"))
            .isDisplayed

    }

    @Test
    fun testCookies() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()))
        webDriver.get(BASE_URI + "cookies.html")

        devTools.send(Security.enable())
        devTools.send(Security.setIgnoreCertificateErrors(true))

        webDriver.get("https://expired.badssl.com/")

        val actualColor = webDriver.findElement(By.tagName("body")).getCssValue("background-color")

        val expectedColor = Color(255, 0, 0, 1.0)

        assertThat(Color.fromString(actualColor)).isEqualTo(expectedColor)

    }

    @Test
    fun testInterceptor() {

        webDriver.get(BASE_URI + "long-page.html")
        val wait = WebDriverWait(webDriver, Duration.ofSeconds(10L))
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("container"), By.tagName("p")))

        val metrics = devTools.send(Page.getLayoutMetrics())
        val contentSize = metrics.contentSize

        val screenshotBase64 = devTools.send(
            Page.captureScreenshot(
                Optional.empty(), Optional.empty(),
                Optional.of(Viewport(0, 0, contentSize.width, contentSize.height, 1)), Optional.empty(),
                Optional.of(true), Optional.of(true)
            )
        )

        val path = Paths.get("full-page-screenshot-chrome.png")
        Files.write(path, Base64.getDecoder().decode(screenshotBase64))

        assertThat(path).exists()
    }

    @Test
    fun testDomMutation() {
        webDriver.get(BASE_URI)

        val logEvents = webDriver as HasLogEvents

        val executor = webDriver as JavascriptExecutor

        val probablyEvents = AtomicReference<DomMutationEvent>()

        val latch = CountDownLatch(1)

        logEvents.onLogEvent(domMutation { probablyEvents.set(it); latch.countDown() })

        val image = webDriver.findElement(By.tagName("img"))

        val srcImg = "img/kotlin.png"
        executor.executeScript("arguments[0].src = '$srcImg';", image)

        assertThat(latch.await(10, SECONDS)).isTrue

        assertThat(probablyEvents.get().element.getAttribute("src")).endsWith(srcImg)
    }

    @Test
    fun testConsoleEvents() {
        val logEvents = webDriver as HasLogEvents

        val latch = CountDownLatch(4)

        logEvents.onLogEvent(consoleEvent { println("${it.timestamp} [${it.type}] - ${it.messages}"); latch.countDown() })

        webDriver.get(BASE_URI + "console-logs.html")

        assertThat(latch.await(10, SECONDS)).isTrue

    }
}