package com.example.calculadora_compose.ui.calculator.utils

fun isNumber(s: String): Boolean {
    return if (s.isEmpty()) false else s.all { Character.isDigit(it) || s == "." }
}