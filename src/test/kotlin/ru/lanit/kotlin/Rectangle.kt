package ru.lanit.kotlin

class Rectangle(private val height: Int, private val width: Int) {
    val isSquare: Boolean
        get() = height == width
}