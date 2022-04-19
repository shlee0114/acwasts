package com.cloudwatch.slackbot

object LambdaConfiguration {
    val dbURL: String
    val user: String
    val password: String
    val slackBotToken: String
    val slackChannel: String
    val rdsURL: String

    init {
        System.getenv()
            .apply {
                dbURL = get("dbURL") ?: ""
                user = get("user") ?: ""
                password = get("password") ?: ""
                slackBotToken = get("slackBotToken") ?: ""
                slackChannel = get("slackChannel") ?: ""
                rdsURL = get("rdsURL") ?: ""
            }
    }
}
