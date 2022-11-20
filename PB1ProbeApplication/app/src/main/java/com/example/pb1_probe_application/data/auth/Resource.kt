package com.example.pb1_probe_application.data.auth

// code from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}