package com.khush.myapplication.extension

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.checkIsEmpty(): Boolean {
    return this.trim().isEmpty()
}

fun String.isPasswordValid(): Boolean {
    val passwordRegex = "^[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]{8,15}$"
    return this.matches(Regex(passwordRegex))

}