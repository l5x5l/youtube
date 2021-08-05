package com.example.youtube_template.src.main.search.models

data class ThumbnailURL(val url: String, val width: UInt, val height: UInt)

data class VideoThumbnail(val default:ThumbnailURL, val medium:ThumbnailURL, val high:ThumbnailURL, val standard:ThumbnailURL, val maxres:ThumbnailURL)

data class VideoStatistics(val viewCount: Long)

data class VideoSnippet(val publishedAt: String, val channelId : String, val title: String, val description : String, val thumbnails: VideoThumbnail, val channelTitle : String)

data class VideoMeta(val id: String, val snippet: VideoSnippet, val statistics: VideoStatistics)

data class Videos(val items: List<VideoMeta>)

// "search : list" api 이용시 사용할 data class
data class SearchId(val kind : String, val videoId : String)

data class SearchVideoMeta(val id : SearchId, val snippet: VideoSnippet)

data class SearchVideos(val items : List<SearchVideoMeta>)