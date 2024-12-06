package com.khush.myapplication.util

object Constants {
    //    const val BASE_URL = "https://www.test.com/"
    const val BASE_URL = "https://api.openweathermap.org/"

    const val API_GET_WEATHER = "data/2.5/onecall"
    const val lat = 12.9082847623315
    const val lon = 77.65197822993314
    const val units = "imperial"
    const val appId = "b143bb707b2ee117e62649b358207d3e"

    const val VERSION = "v1/"
    const val COMMON = "common/"
    const val USER = "user/$VERSION"
    const val CARD = "card/"
    const val ADMIN = "admin/"
    const val VERIFIED_USER = "verifieduser/"
    const val BANK = "Bank/"
    const val GROUP = "group/"
    const val CATEGORY = "category/"
    const val ACTIVITY = "activity/"
    const val COUNTRY = "country/$VERSION"
    const val SUPPORT = "support/"
    const val ACTIVITY_SUPPORT = "activitySupport/"
    const val UserOrder = "userOrder/"
    const val VerifiedUserRating = "verifiedUserRating/"

    const val API_LOGIN = "${USER}login"
    const val API_SIGNUP = "${USER}signup"
    const val API_STATE_LIST = "${COUNTRY}getStateList"

    const val PARAM_EMAIL = "Email"
    const val PARAM_PASSWORD = "Password"
    const val PARAM_DEVICE_TOKEN = "DeviceToken"

    const val IS_USER_LOGIN = "isUserLogin"
}