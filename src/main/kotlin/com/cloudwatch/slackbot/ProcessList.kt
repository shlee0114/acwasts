package com.cloudwatch.slackbot

import java.sql.ResultSet

data class ProcessList private constructor(
    val id: String,
    val user: String,
    val db: String?,
    val command: String,
    val time: String,
    val state: String,
    val info: String?,
) {
    companion object {
        fun of(res: ResultSet) =
            ProcessList(
                id = res.getString("Id"),
                user = res.getString("User"),
                db = res.getString("db"),
                command = res.getString("Command"),
                time = res.getString("Time"),
                state = res.getString("State"),
                info = res.getString("Info")
            )
    }
}
