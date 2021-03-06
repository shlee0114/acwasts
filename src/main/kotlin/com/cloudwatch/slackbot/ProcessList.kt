package com.cloudwatch.slackbot

import java.sql.ResultSet

data class ProcessList(
    val id: String,
    val user: String,
    val db: String?,
    val command: String,
    val time: String,
    val state: String,
    val info: String?,
) {
    constructor(res: ResultSet) : this(
        id = res.getString("ID"),
        user = res.getString("USER"),
        db = res.getString("DB"),
        command = res.getString("COMMAND"),
        time = res.getString("TIME"),
        state = res.getString("STATE"),
        info = res.getString("INFO")
    )

    override fun toString() =
        "id : $id, user : $user, db : $db, command : $command, time : $time, state : $state, info : $info"
}
