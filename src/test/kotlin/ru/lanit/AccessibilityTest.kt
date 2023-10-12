package ru.lanit

import com.deque.html.axecore.selenium.AxeBuilder
import com.deque.html.axecore.selenium.AxeReporter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AccessibilityTest : BaseTest() {

    @Test
    fun testAccessibility() {
        driver.get(BASE_URI)
        assertThat(driver.title).contains("Selenium WebDriver")

        val results = AxeBuilder().analyze(driver)

        results.violations.forEach { println(it) }
        AxeReporter.writeResultsToJsonFile("accessibility", results)
        println()
    }
}