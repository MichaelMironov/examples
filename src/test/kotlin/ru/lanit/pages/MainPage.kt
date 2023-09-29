package ru.lanit.pages

import org.openqa.selenium.WebDriver
import ru.lanit.pages.component.Header

class MainPage(driver: WebDriver) : BasePage<MainPage>(driver) {

    private val header: Header = Header(driver)

    fun userShouldHaveUsername(username: String): MainPage {
        header.usernameShouldBe(username)
        return this
    }

    override fun waitPageLoaded(): MainPage {
        TODO("Not yet implemented")
    }
}