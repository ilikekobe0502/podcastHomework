package com.neo.pod.repository.remote

import com.neo.pod.repository.data.Data
import com.neo.pod.repository.data.PodCastContent
import com.neo.pod.repository.data.PodCastList
import com.neo.pod.repository.remote.RemoteAPI.Companion.getOkHttpClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PodCastApiClient {
    companion object {
        private val TAG = PodCastApiClient::class.simpleName
        private var sInstance: PodCastApiClient? = null
        private lateinit var mService: PodCastApiClientService

        fun getInstance(): PodCastApiClient? {
            if (sInstance == null) {
                synchronized(PodCastApiClient::class) {
                    if (sInstance == null) {
                        sInstance = PodCastApiClient()
                    }
                }
            }
            return sInstance
        }
    }

    init {
        val url = "http://demo4491005.mockable.io/"
        val client = getOkHttpClient()

        val retrofit = Retrofit.Builder()
            .baseUrl(HttpUrl.get(url))
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mService = retrofit.create(PodCastApiClientService::class.java)
    }


    /*--------------------------------------------------------------------------------------------*/
    /* APIs */

    fun getPodCastList(): Single<Data<PodCastList>> {
        return mService.getPodCastList()
    }

    fun getPodCastDetail(): Single<Data<PodCastContent>> {
        return mService.getPodCastDetail()
    }
}