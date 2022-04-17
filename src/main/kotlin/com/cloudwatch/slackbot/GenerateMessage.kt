package com.cloudwatch.slackbot

import java.sql.DriverManager
import java.time.LocalDateTime

class GenerateMessage(private val data: CloudWatchEvent) {

    val message: SlackMessage

    init {
        message = generateMessage()
    }

    private fun generateMessage() =
        SlackMessage(
            listOf(
                Attachment(
                    title = data.AlarmName ?: "CloudWatch Alarm",
                    color = data.NewStateValue.getColor(),
                    fields = listOf(
                        generateWhen(),
                        generateDescription(),
                        generateCause(),
                        generateStatus(),
                    ) +
                        if (data.NewStateValue == AlarmStatus.ALARM) {
                            listOf(generateProcessList())
                        } else {
                            listOf()
                        }
                )
            )
        )

    private fun generateWhen() =
        Field(
            title = "발생시각(KST)",
            value = LocalDateTime.parse(data.StateChangeTime.substring(0, 19)).plusHours(9).toString()
        )

    private fun generateDescription() =
        Field(
            title = "설명",
            value = data.AlarmDescription ?: "CloudWatch Alarm ${data.NewStateValue.getMessage()} Event"
        )

    private fun generateCause() =
        data.Trigger.run {
            when (ComparisonOperator) {
                "GreaterThanThreshold" -> ">"
                "GreaterThanOrEqualToThreshold" -> ">="
                "LowerThanOrEqualToThreshold" -> "<="
                "LessThanThreshold" -> "<"
                else -> ComparisonOperator
            }.let {
                Field(
                    title = "원인",
                    value = "${EvaluationPeriods * (Period / 60)}분 동안 ${EvaluationPeriods}회 $MetricName $it $Threshold",
                )
            }
        }

    private fun generateStatus() =
        Field(
            title = "상태",
            value = "${data.OldStateValue.getMessage()} -> ${data.NewStateValue.getMessage()}"
        )

    private fun generateProcessList() =
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
                Field(
                    title = "프로세스 리스트",
                    value = processes.joinToString("\n")
                )
            }
}
