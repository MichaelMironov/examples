package ru.lanit

import org.junit.jupiter.api.Test
import org.openqa.selenium.WindowType
import kotlin.test.assertTrue

class TabsAndWindowsTest : BaseTest() {

    @Test
    fun testNewTab() {
        driver.get(BASE_URI)

        val startPage = driver.windowHandle
        println("Started page - $startPage")

        driver.switchTo().newWindow(WindowType.TAB)

        driver.get(BASE_URI + "web-form.html")
        println("New page - ${driver.windowHandle}")

        assertTrue(driver.windowHandles.size == 2)

        driver.switchTo().window(startPage)
        println("Page after switch - ${driver.windowHandle}")

        driver.close()
        assertTrue(driver.windowHandles.size == 1)
    }

    @Test
    fun testNewWindow() {
        driver.get(BASE_URI)

        val startPage = driver.windowHandle
        println("Started page - $startPage")

        driver.switchTo().newWindow(WindowType.WINDOW)

        driver.get(BASE_URI + "web-form.html")
        println("New page - ${driver.windowHandle}")

        assertTrue(driver.windowHandles.size == 2)

        driver.switchTo().window(startPage)
        println("Page after switch - ${driver.windowHandle}")

        driver.close()
        assertTrue(driver.windowHandles.size == 1)
    }


}