package ru.lanit.bonigarcia.pages

import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.id
import org.openqa.selenium.WebDriver

const val LOGIN_URI = "login-form.html"

class LoginPage(driver: WebDriver) : BasePage(driver) {
    private val usernameInput = id("username")
    private val passwordInput = id("password")
    private val submitButton = cssSelector("button")
    private val successMessage = id("success")

    init {
        visit(LOGIN_URI)
    }

    fun with(username: String, password: String) {
        type(usernameInput, username)
        type(passwordInput, password)
        click(submitButton)
    }

    fun successLoginMessagePresent(): Boolean {
        return isDisplayed(successMessage)
    }

}