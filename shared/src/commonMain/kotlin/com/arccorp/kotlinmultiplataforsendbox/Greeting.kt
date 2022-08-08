package com.arccorp.kotlinmultiplataforsendbox

import RocketLaunch
import daysUntilNewYear
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class Greeting {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Throws(Exception::class)
    suspend fun greeting(): String {
        val rockets: List<RocketLaunch> = httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }
        return "Olá ${Platform()}!" +
                "\nRestam apenas ${daysUntilNewYear()} até o ano novo! 🎅🏼 " +
                "\nO último lançamento bem sucedido foi ${lastSuccessLaunch.launchDateUTC} 🚀"
    }
}