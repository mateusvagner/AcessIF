package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.dto.SummaryDto
import com.mv.acessif.network.service.SummaryService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class KtorSummaryService
    @Inject
    constructor(private val client: HttpClient) : SummaryService {
        override suspend fun postSummarize(transcriptionId: Int): SummaryDto {
            return client.post {
                url(HttpRoutes.SUMMARIZE) {
                    appendPathSegments(transcriptionId.toString())
                }
            }.body()
        }
    }
