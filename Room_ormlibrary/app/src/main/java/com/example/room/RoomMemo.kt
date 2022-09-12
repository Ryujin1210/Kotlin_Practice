package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_memo")
class RoomMemo {
    @PrimaryKey(autoGenerate = true) // no값이 없을때 자동증가된 숫자값을 db에 입력해준다.
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var content: String = ""

    @ColumnInfo (name = "date")
    var datetime: Long = 0

    constructor(content:String, datetime:Long) {
        this.content = content
        this.datetime = datetime
    }
}