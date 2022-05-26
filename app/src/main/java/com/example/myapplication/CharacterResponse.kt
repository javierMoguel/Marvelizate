package com.example.myapplication

import java.lang.Exception

data class CharacterResponse ( var status: String, var data: dataResponse )

data class dataResponse ( var results: List< resultsResponse > )

data class resultsResponse (
    var id: Int,
    var name: String,
    var description: String,
    var thumbnail: thumbnailResponse,
    var resourceURI: String,
    var comics: comicsResponse
        )

data class comicsResponse (
    var available: Int,
    var collectionURI: String,
    var items: List<ItemsComicResponse>
        )

data class thumbnailResponse (
    var path: String,
    var extension: String
        )

data class ItemsComicResponse(
    var resourceURI: String,
    var name: String

)