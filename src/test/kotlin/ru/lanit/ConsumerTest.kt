package ru.lanit

import org.junit.jupiter.api.Test
import ru.lanit.pages.ConsumerPage

class ConsumerTest {

    @Test
    fun test() {
        val threadLocal = ThreadLocal<String>()
        ConsumerPage()
            .sendReqAndGetResp(threadLocal::set, "Request")
            .doSmth()

        println(threadLocal.get())
    }

}