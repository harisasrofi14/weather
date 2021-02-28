package com.example.myweather.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Weather")
@Parcelize
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "temperature")
    var temp: String? = null,
    @ColumnInfo(name ="main")
    var main : String? = null,
    @ColumnInfo(name = "min_temperature")
    var min_temp: String? = null,
    @ColumnInfo(name = "max_temperature")
    var max_temp: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "sunrise")
    var sunrise: Long = 0,
    @ColumnInfo(name = "sunset")
    var sunset: Long = 0,
    @ColumnInfo(name = "wind")
    var wind: String? = null,
    @ColumnInfo(name = "pressure")
    var pressure: Int = 0,
    @ColumnInfo(name = "humidity")
    var humidity: Int = 0,
    @ColumnInfo(name = "created_by")
    var createdBy: String? = null,
    @ColumnInfo(name = "Modified_Date")
    var modifiedDate: Long = 0
) : Parcelable
