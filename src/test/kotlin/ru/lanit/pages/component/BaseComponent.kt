package ru.lanit.pages.component

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class BaseComponent<T : BaseComponent<T>>(
    protected val self: By,
    protected val driver: WebDriver,
    protected val wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(10L))
) {

}