package com.cloudwatch.slackbot

object LambdaConfiguration {
    val dbURL: String
    val user: String
    val password: String
    val HOOK_URL: String

    init {
        System.getenv()
            .apply {
                dbURL = get("dbURL") ?: ""
                user = get("user") ?: ""
                password = get("password") ?: ""
                HOOK_URL = get("hookUrl") ?: ""
            }
    }
}
