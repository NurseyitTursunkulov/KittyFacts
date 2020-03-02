package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FactItemModel(
    @PrimaryKey(autoGenerate = true) var factNumber: Int,
    @SerializedName("fact")
    var factDescription: String = ""
)

data class FactListModel(
    @SerializedName("data")
    var data: List<FactItemModel>
)