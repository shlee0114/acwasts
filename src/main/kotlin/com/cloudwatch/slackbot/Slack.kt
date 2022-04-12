package com.cloudwatch.slackbot

object Slack {
    val ENCRYPTED_HOOK_URL: String
    val SLACK_CHANNEL: String
    val HOOK_URL: String

    init {
        System.getenv()
            .apply {
                ENCRYPTED_HOOK_URL = get("hookUrl") ?: ""
                SLACK_CHANNEL = get("slackChannel") ?: ""
                HOOK_URL = "$ENCRYPTED_HOOK_URL?LambdaFunctionName=${get("AWS_LAMBDA_FUNCTION_NAME")}"
            }
    }
}
