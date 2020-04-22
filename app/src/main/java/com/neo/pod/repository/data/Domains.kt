package com.neo.pod.repository.data

import java.io.Serializable


/*
    Login Page
 */
data class Data<T>(
        var data: T?
) : Serializable

data class PodCastList(
        var podcast: ArrayList<PodCast>
) : Serializable

data class PodCast(
        var artistName: String?,
        var artworkUrl100: String?,
        var id: Long,
        var name: String?
) : Serializable

data class PodCastContent(
        var collection: PodCastCollection?
) : Serializable

data class PodCastCollection(
        var artistId: Long,
        var artistName: String,
        var artworkUrl100: String,
        var artworkUrl600: String,
        var collectionId: Long,
        var collectionName: String,
        var contentFeed: ArrayList<ContentFeed>
) : Serializable

data class ContentFeed(
        var contentUrl: String?,
        var desc: String?,
        var publishedDate: String?,
        var title: String?
) : Serializable
