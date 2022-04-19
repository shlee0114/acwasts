package com.cloudwatch.slackbot

import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.Attachments
import com.slack.api.model.Field
import java.sql.DriverManager
import java.time.LocalDateTime

class Message(private val data: CloudWatchEvent) {
    fun sendMessage(): Unit =
        Slack.getInstance()
            .methods(LambdaConfiguration.slackBotToken)
            .run {
                chatPostMessage {
                    it.channel(LambdaConfiguration.slackChannel)
                        .attachments(
                            Attachments.asAttachments(
                                Attachment()
                                    .apply {
                                        title = data.AlarmName ?: "CloudWatch Alarm"
                                        color = data.NewStateValue.getColor()
                                        fields = listOf(
                                            generateWhen(),
                                            generateDescription(),
                                            generateCause(),
                                            generateStatus(),
                                            generateLink(),
                                        )
                                    }
                            )
                        )
                }.apply {
                    if (data.NewStateValue == AlarmStatus.ALARM) {
                        filesUpload {
                            it.channels(listOf(LambdaConfiguration.slackChannel))
                                .threadTs(ts)
                                .filename("processList.txt")
                                .filetype("txt")
                                .fileData(getProcessList().toByteArray())
                        }
                    }
                }
            }

    private fun generateWhen() =
        Field()
            .apply {
                title = "발생시각(KST)"
                value = LocalDateTime.parse(data.StateChangeTime.substring(0, 19)).plusHours(9).toString()
            }

    private fun generateDescription() =
        Field()
            .apply {
                title = "설명"
                value = data.AlarmDescription ?: "CloudWatch Alarm ${data.NewStateValue.getMessage()} Event"
            }

    private fun generateCause() =
        data.Trigger.run {
            when (ComparisonOperator) {
                "GreaterThanThreshold" -> ">"
                "GreaterThanOrEqualToThreshold" -> ">="
                "LowerThanOrEqualToThreshold" -> "<="
                "LessThanThreshold" -> "<"
                else -> ComparisonOperator
            }.let {
                Field()
                    .apply {
                        title = "원인"
                        value =
                            "${EvaluationPeriods * (Period / 60)}분 동안 ${EvaluationPeriods}회 $MetricName $it $Threshold"
                    }
            }
        }

    private fun generateStatus() =
        Field()
            .apply {
                title = "상태"
                value = "${data.OldStateValue.getMessage()} -> ${data.NewStateValue.getMessage()}"
            }

    private fun generateLink() =
        Field()
            .apply {
                title = "RDS 바로가기"
                value = LambdaConfiguration.rdsURL
            }

    private fun getProcessList() =
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
                        prepareStatement("select * from INFORMATION_SCHEMA.PROCESSLIST where COMMAND != 'Sleep'")
                            .executeQuery()
                            .let {
                                while (it.next()) {
                                    processes.add(ProcessList.of(it))
                                }
                            }
                    }
                processes.joinToString("\n")
            }
}
