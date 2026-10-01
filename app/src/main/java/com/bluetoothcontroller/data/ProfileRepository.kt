package com.bluetoothcontroller.data

import com.bluetoothcontroller.controller.ControllerProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProfileRepository {
    
    // Stub implementation using flowOf for simplicity in this example
    // Should be backed by SharedPreferences or DataStore

    fun getProfiles(): Flow<List<ControllerProfile>> {
        return flowOf(getDefaultProfiles())
    }

    fun getProfile(id: String): Flow<ControllerProfile?> {
        return flowOf(getDefaultProfiles().find { it.id == id })
    }

    suspend fun saveProfile(profile: ControllerProfile) {
        // Logic to save
    }

    suspend fun deleteProfile(id: String) {
        // Logic to delete
    }

    suspend fun duplicateProfile(id: String, newName: String) {
        // Logic to duplicate
    }

    fun getDefaultProfiles(): List<ControllerProfile> {
        return listOf(
            ControllerProfile("1", "Default Gamepad"),
            ControllerProfile("2", "Media Controller")
        )
    }
}
