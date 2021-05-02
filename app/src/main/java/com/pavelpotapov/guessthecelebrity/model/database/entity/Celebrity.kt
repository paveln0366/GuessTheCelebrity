package com.pavelpotapov.guessthecelebrity.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_celebrity")
data class Celebrity(
        @PrimaryKey
        @SerializedName("position")
        @Expose
        val position: Int,
        @SerializedName("rank")
        @Expose
        val rank: Int,
        @SerializedName("name")
        @Expose
        val name: String,
        @SerializedName("lastName")
        @Expose
        val lastName: String,
        @SerializedName("uri")
        @Expose
        val uri: String,
        @SerializedName("imageUri")
        @Expose
        val imageUri: String,
        @SerializedName("age")
        @Expose
        val age: Int,
        @SerializedName("source")
        @Expose
        val source: String,
        @SerializedName("industry")
        @Expose
        val industry: String,
        @SerializedName("gender")
        @Expose
        val gender: String,
        @SerializedName("country")
        @Expose
        val country: String,
        @SerializedName("category")
        @Expose
        val category: String,
        @SerializedName("earnings")
        @Expose
        val earnings: Double,
        @SerializedName("squareImage")
        @Expose
        val squareImage: String
) {
    fun getImageUrl(): String {
        return "https$squareImage"
    }
}
