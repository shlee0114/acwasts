package com.cloudwatch.slackbot

data class CloudWatchEvent(
    val AlarmName: String?,
    val AlarmDescription: String?,
    val AWSAccountId: String?,
    val AlarmConfigurationUpdatedTimestamp: String?,
    val NewStateValue: String?,
    val NewStateReason: String?,
    val StateChangeTime: String?,
    val Region: String?,
    val AlarmArn: String?,
    val OldStateValue: String?,
    val OKActions: Collection<Any?>?,
    val AlarmActions: Collection<String>?,
    val InsufficientDataActions: Collection<Any?>?,
    val Trigger: Trigger?,
)

data class Trigger(
    val MetricName: String?,
    val Namespace: String?,
    val StatisticType: String?,
    val Statistic: String?,
    val Unit: String?,
    val Dimensions: Collection<Dimension?>?,
    val Period: Int?,
    val EvaluationPeriods: Int?,
    val DatapointsToAlarm: Int?,
    val ComparisonOperator: String?,
    val Threshold: Double?,
    val TreatMissingData: String?,
    val EvaluateLowSampleCountPercentile: String?
)

data class Dimension(
    val value: String,
    val name: String,
)
