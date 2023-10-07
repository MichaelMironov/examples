package ru.lanit

import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.id
import org.openqa.selenium.chrome.ChromeOptions

class NotificationTest : BaseTest() {

    @BeforeEach
    fun setup() {
        val options = ChromeOptions()
        val prefs = HashMap<String, Any>()

        prefs["profile.default_content_setting_values.notifications"] = 1 // 2 -> block notifications
        options.setExperimentalOption("prefs", prefs)

//        driver = WebDriverManager.chromedriver().capabilities(options).create()
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun testVideoAndMicro() {
        val options = ChromeOptions()
        options.addArguments("--use-fake-ui-for-media-stream")
        options.addArguments("--use-fake-device-for-media-stream")

        driver = WebDriverManager.chromedriver().capabilities(options).create()

        driver.get(BASE_URI + "get-user-media.html")

        driver.findElement(id("start")).click()

        Thread.sleep(5000L)
    }

    @Test
    fun testNotification() {
        driver.get(BASE_URI + "notifications.html")
        val script = """
            const callback = arguments[arguments.length - 1];
            const OldNotify = window.Notification;
            function newNotification(title, options) {
                callback(title);
                return new OldNotify(title, options);
            }
            newNotification.requestPermission = OldNotify.requestPermission.bind(OldNotify);
            Object.defineProperty(newNotification, 'permission', {
                get: function() {
                    return OldNotify.permission;
                }
            });
            window.Notification = newNotification;
            document.getElementById('notify-me').click();
        """.trimIndent()

        val notificationTitle = js.executeAsyncScript(script)
        assertThat(notificationTitle).isEqualTo("This is a notification")
    }

}