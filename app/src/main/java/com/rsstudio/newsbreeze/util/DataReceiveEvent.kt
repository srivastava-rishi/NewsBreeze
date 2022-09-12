package com.rsstudio.newsbreeze.util

import com.rsstudio.newsbreeze.data.network.model.ArticleData

class DataReceiveEvent {

    private var news: ArticleData? = null
    private var eventTag: EventTagType = EventTagType.EVENT_NONE


    fun isTagMatchWith(eventTag: EventTagType): Boolean {
        return this.eventTag == eventTag
    }

    constructor(eventTag: EventTagType, news: ArticleData) {
        this.eventTag= eventTag
        this.news = news
    }

    fun getNews():ArticleData?{
        return this.news
    }
}