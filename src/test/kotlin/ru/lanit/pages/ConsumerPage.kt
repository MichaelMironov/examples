package ru.lanit.pages

import java.util.function.Consumer

class ConsumerPage {

    fun sendReqAndGetResp(consumer: Consumer<String>, req: String): ConsumerPage {
        consumer.accept("Response $req")
        return this
    }

    fun doSmth() {

    }
}