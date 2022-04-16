package com.cloudwatch.slackbot

object Slack {
    val HOOK_URL: String

    init {
        System.getenv()
            .apply {
                HOOK_URL = get("hookUrl") ?: ""
            }
    }
}
