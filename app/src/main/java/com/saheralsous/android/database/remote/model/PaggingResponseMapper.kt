package com.saheralsous.android.database.remote.model

interface PagingResponseMapper<Response, Model> {

    abstract fun mapFromResponse(response: Response) : Model
}