package com.example.myapplication

import java.lang.Exception

data class ComicResponse ( var status: String, var data: dataComicResponse )

data class dataComicResponse ( var results: List< resultsComicResponse > )

data class resultsComicResponse (
    var id: Int,
    var title: String,
    var description: String,
    var textObjects: List<textObjectsResponse>,
    var urls: List<urlResponse>
        )

data class textObjectsResponse(
    var text: String
)

data class urlResponse(
    var type: String,
    var url: String
)
