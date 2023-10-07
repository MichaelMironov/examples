package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.*
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ByIdOrName
import org.openqa.selenium.support.locators.RelativeLocator.with
import org.openqa.selenium.support.pagefactory.ByAll
import org.openqa.selenium.support.pagefactory.ByChained
import org.openqa.selenium.support.ui.WebDriverWait
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import kotlin.test.assertNotNull

class Main {

    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeEach
    fun setup() {
//        driver = RemoteWebDriver.builder()
//            .oneOf(SafariOptions())
//            .addAlternative(ChromeOptions()).build()
        driver = WebDriverManager.chromedriver().create()
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html")

        wait = WebDriverWait(driver, Duration.ofSeconds(10L))
    }

    @Test
    fun openSite() {

        val element = driver.findElement(tagName("textarea"))

        assertThat(element.getDomAttribute("rows")).isEqualTo("3")
    }

    @Test
    fun testElementsCollection() {


        val elements: List<WebElement> = driver.findElements(className("form-control"))

        val textInput = driver.findElement(name("my-text"))
        assertThat(textInput.isEnabled).isTrue

        assertThat(textInput.getDomAttribute("myprop")).isEqualTo("myvalue")
        assertThat(textInput.getDomProperty("myprop")).isNull()


        assertThat(elements.size).isPositive
        assertThat(elements.elementAt(0).getAttribute("name"))
            .isEqualTo("my-text")
    }

    @Test
    fun testByLinkText() {

        val elementByText = driver.findElement(linkText("Return to index"))

        assertThat(elementByText.tagName).isEqualTo("a")
        assertThat(elementByText.getCssValue("cursor")).isEqualTo("pointer")

        val elementByPartialLint = driver.findElement(partialLinkText("index"))

        assertThat(elementByPartialLint.location).isEqualTo(elementByText.location)
    }

    @Test
    fun testByCssSelectorAdvanced() {

        val checkbox1 = driver.findElement(cssSelector("[type=checkbox]:checked"))
        val checkbox2 = driver.findElement(cssSelector("[type=checkbox]:not(:checked)"))

        assertThat(checkbox1.isSelected).isTrue
        assertThat(checkbox2.isSelected).isFalse
        assertThat(checkbox2.accessibleName).isEqualTo("Default checkbox")
    }

    @Test
    fun testByXPathAdvanced() {
        val radioButton1 = driver.findElement(xpath("//*[@type='radio' and @checked]"))
        assertThat(radioButton1.isSelected).isTrue
    }

    @Test
    fun testByIdOrName() {

        val element = driver.findElement(ByIdOrName("my-file"))

        assertThat(element.getAttribute("id")).isBlank
        assertThat(element.getAttribute("name")).isNotBlank
    }

    @Test
    fun testByChained() {
        val rows = driver.findElements(ByChained(tagName("form"), className("row")))

        assertThat(rows).hasSize(1)

        val rowsInForm = driver.findElements(ByAll(tagName("form"), className("row")))

        assertThat(rowsInForm).hasSize(5)

    }

    @Test
    fun testRelativeLocators() {

        val link = driver.findElement(partialLinkText("Return"))

        val inputAboveLink = driver.findElement(with(tagName("input")).above(link))

        assertNotNull(inputAboveLink.getAttribute("readonly"))
    }

    @Test
    fun testCalendar() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = driver.findElement(name("my-date"))
        datePicker.click()

        val monthElement = driver.findElement(xpath("//th[contains(text(), '$year')]"))
        monthElement.click()

        val leftArrow = driver.findElement(with(tagName("th")).toRightOf(monthElement))
        leftArrow.click()

        val monthPastYear = driver.findElement(with(cssSelector("span[class$=focused]")).below(leftArrow))
        monthPastYear.click()

        val dayElement = driver.findElement(xpath("//td[@class='day' and contains(text(), '$day')]"))
        dayElement.click()

        val dateAfterChange = datePicker.getAttribute("value")

        println("Date after change: $dateAfterChange")

        calendar.add(Calendar.YEAR, -1)
        val previousYear = calendar.time

        val dateFormat = SimpleDateFormat("MM/dd/yyyy")

        val expectedDate = dateFormat.format(previousYear)

        println("Expected date: $expectedDate")
        assertThat(dateAfterChange).isEqualTo(expectedDate)
    }

    @Test
    fun testSendKeys() {

        val startUrl = "https://bonigarcia.dev/selenium-webdriverjava/web-form.html"

        val inputFile = driver.findElement(name("my-file"))

        val file = Files.createTempFile("tempfiles", ".tmp")

        val filename = file.toAbsolutePath().toString()

        inputFile.sendKeys(filename)

        driver.findElement(tagName("form")).submit()

        assertThat(driver.currentUrl).isNotEqualTo(startUrl)
    }


    @Test
    fun testSlider() {
        val slider = driver.findElement(name("my-range"))

        val startPosition = slider.getAttribute("value")

        println("Start slider position = $startPosition")

        for (i in 1..5) {
            slider.sendKeys(Keys.ARROW_RIGHT)
        }

        val endPosition = slider.getAttribute("value")

        assertThat(startPosition).isNotEqualTo(endPosition)
    }


}