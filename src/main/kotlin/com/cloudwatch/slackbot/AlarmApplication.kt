package com.cloudwatch.slackbot

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.HttpURLConnection
import java.net.URL

class AlarmApplication : RequestHandler<Map<String, Any>, HashMap<String, Any>> {

    private val jackson = jacksonObjectMapper()

    override fun handleRequest(input: Map<String, Any>, context: Context?): HashMap<String, Any> {
        val (data, otherData) = input["Records"].toString().let {
            getMessage(it) to getDataWithoutMessage(it)
        }

        val message = "{text : \"${data.AlarmName} state is now ${data.NewStateValue}: ${data.NewStateReason}\"}"

        val connection = (URL(Slack.HOOK_URL).openConnection() as HttpURLConnection)
            .apply {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/json; utf-8")
                outputStream
                    .apply {
                        write(message.toByteArray(charset = Charsets.UTF_8))
                    }
            }
        connection.inputStream

        println(message)

        val headers = hashMapOf<String, String>()
        headers["Access-Control-Allow-Origin"] = "*"

        val response = hashMapOf<String, Any>()
        response["isBase64Encoded"] = true
        response["headers"] = headers
        response["statusCode"] = 200
        response["body"] = message

        return response
    }

    private fun getMessage(input: String): CloudWatchEvent =
        jackson.readValue(
            input.run {
                substring(indexOf("Message=") + 8, indexOf("}}") + 2)
            }
        )

    private fun getDataWithoutMessage(input: String): Map<String, String> =
        input.run {
            substring(2, indexOf("Message=")) + substring(indexOf("}}") + 3, length - 4)
        }.let { nonJsonString ->
            nonJsonString.split(", ").associate {
                it.split("=")[0] to it.split("=")[1]
            }
        }
}
