package ru.lanit

import org.junit.jupiter.api.Test

typealias IntUnaryOp = (Int) -> Int
typealias IntBinOp = (Int) -> (Int) -> Int

class FunctionsTest {

    @Test
    fun test() {

        fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = {
            f(g(it))
        }

        fun square(n: Int) = n * n
        fun triple(n: Int) = 3 * n

        val squareOfTriple = compose(::square, ::triple)

        println(squareOfTriple(5))

        val unite = { x: (Int) -> Int -> { y: (Int) -> Int -> { z: Int -> x(y(z)) } } }

        val compose: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp = { x ->
            { y -> { z -> (x(y(z))) } }
        }
        println(compose(squareOfTriple))
    }


    @Test
    fun funTest2() {
        val add: (Int) -> (Int) -> Int = { a -> { b -> a + b } }
        println("ADD - " + add(5)(6))
    }

    @Test
    fun testFun3() {
        val taxRate: Double = 0.09
        val addTax = {rate: Double, price: Double -> price * rate}

        println(addTax(taxRate, 12.0))
    }

}