package ru.lanit

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NavigationTest : BaseTest() {

    @Test
    fun testNavigationHistory() {
        val firstPage = BASE_URI + "navigation1.html"
        val secondPage = BASE_URI + "navigation2.html"
        val thirdPage = BASE_URI + "navigation3.html"

        driver.get(firstPage)

        driver.navigate().to(secondPage)
        driver.navigate().to(thirdPage)
        driver.navigate().back()
        driver.navigate().forward()
        driver.navigate().refresh()

        assertEquals(driver.currentUrl, thirdPage)
    }
}