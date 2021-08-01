package com.yash2108.imagelookup.network

object RequestBuilder {

    fun buildPhotoRequest(query: String?, page: Long?, pagesize: Long?): HashMap<String, Any> {
        val map = HashMap<String, Any>()

        if (query?.isNotBlank() == true) map["tags"] = query

        if (page != null) map["page"] = page

        if (pagesize != null) map["per_page"] = pagesize

        map["method"] = "flickr.photos.search"
        map["api_key"] = "062a6c0c49e4de1d78497d13a7dbb360"
        map["format"] = "json"
        map["nojsoncallback"] = 1

        return map
    }

}