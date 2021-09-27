package com.akshatsahijpal.crud.util

sealed class Resource <R>(data: R? = null, message: String? = null){
    class Success <R> (data: R): Resource<R>(data = data)
    class Failed <R> (message: String): Resource<R>(message = message)
    class Loading <R> (data: R?, message: String): Resource<R>(data, message)
}