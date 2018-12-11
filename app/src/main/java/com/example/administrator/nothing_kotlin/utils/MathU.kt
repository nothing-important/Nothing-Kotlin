package com.example.administrator.nothing_kotlin.utils

import java.math.BigDecimal

class MathU {

    companion object {
        /**
         * valueOne:除数
         * valueTwo:被除数
         * scale:保留几位小数
         */
        fun divide(valueOne: Int, valueTwo: Int, scale: Int): Double {
            if (scale < 0) {
                throw IllegalArgumentException("The scale must be a positive integer or zero")
            }
            val b1 = BigDecimal(java.lang.Double.toString(valueOne.toDouble()))
            val b2 = BigDecimal(java.lang.Double.toString(valueTwo.toDouble()))
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
        }
    }

}