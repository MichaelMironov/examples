package ru.lanit.pages.component

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.textToBe

class Header(driver: WebDriver) : BaseComponent<Header>(
    By.className("navbar"),
    driver
) {
    private val usernameProfile: By = By.id("userDropdown")

    fun usernameShouldBe(username: String) {
        wait.until(
            textToBe(
                usernameProfile,
                username
            )
        )
    }
}