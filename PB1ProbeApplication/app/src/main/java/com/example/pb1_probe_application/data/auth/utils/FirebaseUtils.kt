package com.example.pb1_probe_application.data.auth.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

// code from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if(it.exception != null){
                cont.resumeWithException(it.exception!!)
            }else{
                cont.resume(it.result, null)
            }
        }
    }
}