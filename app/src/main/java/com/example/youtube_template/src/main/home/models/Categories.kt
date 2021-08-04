package com.example.youtube_template.src.main.home.models

data class CategorySnippet(val title : String, val assignable : Boolean, val channelId : String)

data class CategoryMeta(val id : String, val snippet : CategorySnippet)

data class Categories (val items : List<CategoryMeta>)