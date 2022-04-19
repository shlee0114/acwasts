package com.cloudwatch.slackbot

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class AlarmApplication : RequestHandler<Map<String, Any>, HashMap<String, Any>> {

    private val jackson = jacksonObjectMapper()

    override fun handleRequest(input: Map<String, Any>, context: Context?): HashMap<String, Any> {
        Message(getMessage(input["Records"]!!)).sendMessage()

        return hashMapOf<String, Any>()
            .apply {
                set("statusCode", 200)
            }
    }

    private fun getMessage(input: Any): CloudWatchEvent =
        jackson.readValue(
            input.toString().run {
                substring(indexOf("Message=") + 8, indexOf("}}") + 2)
            }
        )
}
