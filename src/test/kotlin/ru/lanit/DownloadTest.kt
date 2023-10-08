package ru.lanit

import org.apache.commons.io.FileUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import java.io.File

class DownloadTest : BaseTest() {

    @Test
    fun testDownload() {
        driver.get(BASE_URI + "download.html")

        val pngLink = driver.findElement(By.xpath("(//a)[2]"))
        val pngFile = File(".", "webdrivermanager.png")
        download(pngLink.getAttribute("href"), pngFile)
        assertThat(pngFile).exists()
    }

    private fun download(link: String?, destination: File) {
        HttpClientBuilder.create().build().use { client ->
            client.execute(HttpGet(link)) { FileUtils.copyInputStreamToFile(it.entity.content, destination) }
        }
    }
}