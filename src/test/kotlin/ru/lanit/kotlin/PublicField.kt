package ru.lanit.kotlin

class PublicField {
    var counter = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}

fun main() {
    val field = PublicField()
    println(field.addWord("Mike"))
}