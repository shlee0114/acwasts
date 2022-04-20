package com.cloudwatch.slackbot

data class CloudWatchEvent(
    val AlarmName: String?,
    val AlarmDescription: String?,
    val AWSAccountId: String?,
    val AlarmConfigurationUpdatedTimestamp: String?,
    val NewStateValue: AlarmStatus,
    val NewStateReason: String?,
    val StateChangeTime: String,
    val Region: String?,
    val AlarmArn: String?,
    val OldStateValue: AlarmStatus,
    val OKActions: Collection<Any?>?,
    val AlarmActions: Collection<String>?,
    val InsufficientDataActions: Collection<Any?>?,
    val Trigger: Trigger,
)

data class Trigger(
    val MetricName: String?,
    val Namespace: String?,
    val StatisticType: String?,
    val Statistic: String?,
    val Unit: String?,
    val Dimensions: Collection<Dimension?>?,
    val Period: Int,
    val EvaluationPeriods: Int,
    val DatapointsToAlarm: Int?,
    val ComparisonOperator: String?,
    val Threshold: Double?,
    val TreatMissingData: String?,
    val EvaluateLowSampleCountPercentile: String?,
)

data class Dimension(
    val value: String,
    val name: String,
)

enum class AlarmStatus {
    ALARM {
        override fun getColor() = "danger"
        override fun getMessage() = "위험"
    },
    INSUFFICIENT_DATA {
        override fun getColor() = "warning"
        override fun getMessage() = "데이터 부족"
    },
    OK {
        override fun getColor() = "good"
        override fun getMessage() = "정상"
    };

    abstract fun getColor(): String
    abstract fun getMessage(): String
}
