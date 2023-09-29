package ru.lanit.pages

import org.openqa.selenium.By
import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.By.id
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements

class LoginPage(driver: WebDriver) : BasePage<LoginPage>(driver) {

    private val usernameInput: By = id("username")
    private val passwordInput: By = id("password")
    private val submitButton: By = cssSelector("[type='submit']")

    fun loginAs(login: String, password: String): MainPage {
        input(login, usernameInput)
        input(password, passwordInput)
        click(submitButton)
        return MainPage(driver)
    }

    override fun waitPageLoaded(): LoginPage {
        wait.until(
            visibilityOfAllElements(
                driver.findElement(usernameInput),
                driver.findElement(passwordInput)
            )
        )
        return this
    }

    fun open(): LoginPage {
        driver.get("https://at-sandbox.workbench.lanit.ru/login/")
        return this;
    }
}

