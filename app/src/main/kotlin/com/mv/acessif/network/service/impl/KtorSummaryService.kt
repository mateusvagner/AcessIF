package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.dto.SummaryDto
import com.mv.acessif.network.service.SummaryService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class KtorSummaryService
    @Inject
    constructor(private val client: HttpClient) : SummaryService {
        override suspend fun postSummarize(transcriptionId: Int): SummaryDto {
            return client.get {
                url("${HttpRoutes.SUMMARIZE}/$transcriptionId")
            }.body()
        }
    }
