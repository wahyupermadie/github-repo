package com.wahyupermadie.myapplication.utils.network

import org.json.JSONObject
import retrofit2.Response


suspend fun <T: Any> safeCallApi(
    call: suspend () -> Response<T>
): State<T> {

    var responseCode: Int? = null
    var errorMessage: String? = null

    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                return State.Success(it)
            } ?: run {
                return State.Failure( null, "Data Corrupt" , 422)
            }
        } else {
            responseCode = response.code()

            val objError = JSONObject(response.errorBody()?.string())
            errorMessage = objError.getString("error")

            return State.Failure(
                null, message = errorMessage, responseCode = responseCode
            )
        }
    } catch (e: Exception) {
        return State.Failure(
           e, message = errorMessage, responseCode = responseCode
        )
    }
}