package ru.lanit.condition

import org.assertj.core.api.Assertions.assertThat
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition

class PhotoCondition(private val expectedPhoto: String, private val actualPhoto: String) : ExpectedCondition<Boolean> {

    override fun apply(input: WebDriver?): Boolean {
        //TODO: encode Base64 photo
        assertThat(expectedPhoto).isBase64
        assertThat(actualPhoto).isBase64
        return expectedPhoto == actualPhoto
    }
}