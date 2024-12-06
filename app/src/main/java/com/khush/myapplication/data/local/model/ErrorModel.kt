package com.khush.myapplication.data.local.model

data class ErrorModel(
    val errorField : ErrorField,
    val errorMessage : String,
    val from : Int? = null
)

enum class ErrorField {
    EMAIL,
    PASSWORD,
    FIRST_NAME,
    LAST_NAME
}
