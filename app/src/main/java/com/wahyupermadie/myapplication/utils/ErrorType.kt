package com.wahyupermadie.myapplication.utils

sealed class ErrorType {
    data class NetworkErrorException(val errCode: Int, val message: String) : ErrorType()
    data class UnknownHostException(val message: String) : ErrorType()
}