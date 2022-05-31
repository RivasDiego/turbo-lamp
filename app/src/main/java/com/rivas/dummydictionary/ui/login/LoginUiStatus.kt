package com.rivas.dummydictionary.ui.login

sealed class LoginUiStatus {
    object Resume: LoginUiStatus()
    object Loading: LoginUiStatus()
    class Error(val exception: Exception): LoginUiStatus()
    class ErrorWithMessage(val message: String): LoginUiStatus() // esto es mio
    data class Success(val token: String): LoginUiStatus()
}