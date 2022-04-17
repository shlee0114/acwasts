package com.cloudwatch.slackbot

data class SlackMessage(
    val attachments: Collection<Attachment>
)

data class Attachment(
    val title: String,
    val color: String,
    val fields: Collection<Field>,
)

data class Field(
    val title: String,
    val value: String,
)
