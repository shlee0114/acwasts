package com.cloudwatch.slackbot

object LambdaConfiguration {
    val dbURL: String
    val user: String
    val password: String
    val hookURL: String
    val slackBotToken: String
    val slackChannel: String

    init {
        System.getenv()
            .apply {
                dbURL = get("dbURL") ?: ""
                user = get("user") ?: ""
                password = get("password") ?: ""
                hookURL = get("hookURL") ?: ""
                slackBotToken = get("slackBotToken") ?: ""
                slackChannel = get("slackChannel") ?: ""
            }
    }
}
