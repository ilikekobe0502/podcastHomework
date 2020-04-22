package com.neo.pod.repository

import android.app.Application
import com.google.gson.Gson
import com.neo.pod.repository.data.Data
import com.neo.pod.repository.data.PodCastContent
import com.neo.pod.repository.data.PodCastList
import com.neo.pod.repository.provider.preferences.SharedPreferencesProvider
import com.neo.pod.repository.remote.RemoteAPI
import com.neo.pod.repository.remote.PodCastApiClient
import io.reactivex.Single

class Repository(
        private var application: Application,
        private val sharedPreferencesProvider: SharedPreferencesProvider
) {

    val TAG = Repository::class.simpleName

    val mGson: Gson = Gson()

    init {
        RemoteAPI.init(application)
    }

    /*
        Remote API
     */

    fun getPodCastList(): Single<Data<PodCastList>> {
        return PodCastApiClient.getInstance()!!.getPodCastList()
    }

    fun getPodCastDetail(): Single<Data<PodCastContent>> {
        return PodCastApiClient.getInstance()!!.getPodCastDetail()
    }

    /*
        Local
     */
}