package com.example.myapplication

import android.provider.BaseColumns

class Contract {

    class Comic: BaseColumns{
        companion object{
            val ID = "id"
            val COMICID = "ComicId"
            val TITLE = "title"
            val PURCHASE = "purchase"
        }
    }
}