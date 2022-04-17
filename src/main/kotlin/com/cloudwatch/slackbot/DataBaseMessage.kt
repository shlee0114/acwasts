package com.cloudwatch.slackbot

import com.slack.api.Slack
import java.sql.DriverManager

class DataBaseMessage {
    private val client = Slack.getInstance()

    fun sendProcessList(): Unit =
        mutableListOf<ProcessList>()
            .let { processes ->

                DriverManager.getConnection(
                    LambdaConfiguration.dbURL,
                    LambdaConfiguration.user,
                    LambdaConfiguration.password
                )
                    .apply {
                        isReadOnly = true
                    }.run {
                        prepareStatement("show processlist")
                            .executeQuery()
                            .let {
                                while (it.next()) {
                                    processes.add(ProcessList.of(it))
                                }
                            }
                    }

                client.methods(LambdaConfiguration.slackBotToken)
                    .filesUpload {
                        it.channels(listOf(LambdaConfiguration.slackChannel))
                            .filename("processList.txt")
                            .filetype("txt")
                            .fileData(processes.joinToString("\n").toByteArray())
                    }
            }
}
