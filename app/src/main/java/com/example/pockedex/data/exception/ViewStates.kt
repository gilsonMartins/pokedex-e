package com.example.pockedex.data.exception

sealed class ViewStates {
    class Sucesso<T>(val list: T) : ViewStates()
    class Aviso(val aviso: String) : ViewStates()
    class Error(val erro: Throwable) : ViewStates()
}
