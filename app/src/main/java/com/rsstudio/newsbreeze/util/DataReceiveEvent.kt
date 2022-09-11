package com.rsstudio.newsbreeze.util

import com.rsstudio.newsbreeze.data.network.model.ArticleData

class DataReceiveEvent {

    private var newsEntity: ArticleData? = null
    private var eventTag: EventTagType = EventTagType.EVENT_NONE


    fun isTagMatchWith(eventTag: EventTagType): Boolean {
        return this.eventTag == eventTag
    }

    constructor(eventTag: EventTagType, messageEntity: ArticleData) {
        this.eventTag= eventTag
        this.newsEntity = messageEntity
    }

    fun getNews():ArticleData?{
        return this.newsEntity
    }
}