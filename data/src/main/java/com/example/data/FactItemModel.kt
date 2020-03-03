package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FactItemModel(
        @PrimaryKey var factNumber: Int,
        @SerializedName("fact")
        var factDescription: String = ""
)

@Entity
data class TotalFactsSize(
        @PrimaryKey
        var size: Int
)

data class FactListModel(
        @SerializedName("data")
        var data: List<FactItemModel>,
        @SerializedName("total")
        var totalItemsSize: Int,
        @SerializedName("from")
        var fromIndex:Int,
        @SerializedName("to")
        var toIndex:Int
)