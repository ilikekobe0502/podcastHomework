package com.neo.pod.repository.remote

import com.neo.pod.repository.data.Data
import com.neo.pod.repository.data.PodCastContent
import com.neo.pod.repository.data.PodCastList
import io.reactivex.Single
import retrofit2.http.*

interface PodCastApiClientService {

    @GET("getcasts")
    fun getPodCastList(): Single<Data<PodCastList>>

    @GET("getcastdetail")
    fun getPodCastDetail(): Single<Data<PodCastContent>>
}