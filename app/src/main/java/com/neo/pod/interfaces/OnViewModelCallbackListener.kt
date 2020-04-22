package com.neo.pod.interfaces

interface OnViewModelCallbackListener {
    fun onSuccess(it: Pair<Int, Any?>? = null)
    fun onError(t: Pair<Int, Throwable?>? = null)
    fun onProgress(b: Pair<Int, Boolean?>? = null)
}