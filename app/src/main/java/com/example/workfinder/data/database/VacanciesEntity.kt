package com.example.workfinder.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workfinder.data.api.Region

@Entity(tableName = "vacancy")
data class Vacancy(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobName: String?,
    val duty: String?,
    val salary: String?,
    val contact_person: String?,
    val email: String?,
    val phone: String?,
    val source: String?,
    val region: String?,
    val employment: String?
)