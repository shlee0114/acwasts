package com.cloudwatch.slackbot

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.*
import kotlin.collections.HashMap

class AlarmApplication : RequestHandler<Map<String, Any>, HashMap<String, Any>> {

    private val base64Decoder = Base64.getDecoder()
    private val jackson = jacksonObjectMapper()

    override fun handleRequest(input: Map<String, Any>?, context: Context?): HashMap<String, Any> {
        val body = hashMapOf<String, Any>()
        return response(200, body)
    }

    private fun response(statusCode: Int, body: Map<String, Any>?): HashMap<String, Any> {
        val headers = hashMapOf<String, String>()
        headers["Access-Control-Allow-Origin"] = "*"

        val response = hashMapOf<String, Any>()
        response["isBase64Encoded"] = true
        response["headers"] = headers
        response["statusCode"] = statusCode

        if (body != null)
            response["body"] = jackson.writeValueAsString(body)

        return response
    }
}
