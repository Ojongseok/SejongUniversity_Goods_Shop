package com.example.sejonggoodsmallproject.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecentSearch")
data class RecentSearchModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var timestamp: String
)