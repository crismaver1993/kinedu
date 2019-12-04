package com.dot7.kinedu.viewModel

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    object Refresh : ScreenState<Nothing>()
    object ErrorServer : ScreenState<Nothing>()
    object InternetError : ScreenState<Nothing>()
    class Render<T>(var renderState: T) : ScreenState<T>()
    class ShowErrorMessage<String>(var errorMessage: String) : ScreenState<String>()
    class ShowErrorInfo<ResponseBody>(var responseBody: ResponseBody) : ScreenState<ResponseBody>()
}