package com.cloudwatch.slackbot

fun main() {
    val alarmApplication = AlarmApplication()

    alarmApplication.handleRequest(
        mapOf(
            "Records" to "[{EventSource=aws:sns, EventVersion=1.0, EventSubscriptionArn=arn:aws:sns:ap-northeast-2:094246554965:test:c7823025-66ce-4c2d-9d4b-26526ddd9a6f, Sns={Type=Notification, MessageId=b4b56481-ee05-5483-8095-3e5278f592e4, TopicArn=arn:aws:sns:ap-northeast-2:094246554965:test, Subject=ALARM: \"awsrds-database-1-High-CPU-\" in Asia Pacific (Seoul), Message={\"AlarmName\":\"awsrds-database-1-High-CPU-\",\"AlarmDescription\":null,\"AWSAccountId\":\"094246554965\",\"AlarmConfigurationUpdatedTimestamp\":\"2022-04-15T15:35:20.670+0000\",\"NewStateValue\":\"ALARM\",\"NewStateReason\":\"Threshold Crossed: 1 out of the last 1 datapoints [5.4417573626227105 (15/04/22 15:50:00)] was greater than or equal to the threshold (5.0) (minimum 1 datapoint for OK -> ALARM transition).\",\"StateChangeTime\":\"2022-04-15T15:52:16.751+0000\",\"Region\":\"Asia Pacific (Seoul)\",\"AlarmArn\":\"arn:aws:cloudwatch:ap-northeast-2:094246554965:alarm:awsrds-database-1-High-CPU-\",\"OldStateValue\":\"OK\",\"OKActions\":[],\"AlarmActions\":[\"arn:aws:sns:ap-northeast-2:094246554965:test\"],\"InsufficientDataActions\":[],\"Trigger\":{\"MetricName\":\"CPUUtilization\",\"Namespace\":\"AWS/RDS\",\"StatisticType\":\"Statistic\",\"Statistic\":\"AVERAGE\",\"Unit\":null,\"Dimensions\":[{\"value\":\"database-1\",\"name\":\"DBInstanceIdentifier\"}],\"Period\":60,\"EvaluationPeriods\":1,\"DatapointsToAlarm\":1,\"ComparisonOperator\":\"GreaterThanOrEqualToThreshold\",\"Threshold\":5.0,\"TreatMissingData\":\"\",\"EvaluateLowSampleCountPercentile\":\"\"}}, Timestamp=2022-04-15T15:52:16.808Z, SignatureVersion=1, Signature=gBptW/BU033m06FbQhohig5+GXOPE/KpQ8QMPg8bpmGummmQDYR/odbqwJG4CHiCKWyqJGSxpXzz9vFiPjCyCVBkHTMhDUAk951YJeyrlgFrJBjMkGqmQhp1l963tPdP2Nxi2c1CLDvR7XgCBMzDxjUPvVeG2SQXbOXz/LlO46B+5R3RkkMfxxnT/LB09rdPR9N+OAB3ic8yNdQz/U8x0HTfQE12QanO3h+SOyNYsHHVBTRlGt2lkMv9dw2e7+KMcEEn/CregptYECkbVVGOcVvQcanT9/eNUqwbLTB04gdmd9EqXO6jWeKF6Iv6kkDRpUxOypYLQux4QBjrUrLODw==, SigningCertUrl=https://sns.ap-northeast-2.amazonaws.com/SimpleNotificationService-7ff5318490ec183fbaddaa2a969abfda.pem, UnsubscribeUrl=https://sns.ap-northeast-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:ap-northeast-2:094246554965:test:c7823025-66ce-4c2d-9d4b-26526ddd9a6f, MessageAttributes={}}}]\n"
        ),
        null
    )
}