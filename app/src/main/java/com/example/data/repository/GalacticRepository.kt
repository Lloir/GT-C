package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.entity.FleetMission
import com.example.data.local.entity.TradeAlert
import com.example.data.local.entity.TradeNotification
import com.example.data.local.entity.TycoonNote
import kotlinx.coroutines.flow.Flow

class GalacticRepository(private val database: AppDatabase) {

    // Trade Alerts
    val allAlerts: Flow<List<TradeAlert>> = database.tradeAlertDao().getAllAlerts()

    suspend fun getActiveAlerts(): List<TradeAlert> {
        return database.tradeAlertDao().getActiveAlerts()
    }

    suspend fun insertAlert(alert: TradeAlert): Long {
        return database.tradeAlertDao().insertAlert(alert)
    }

    suspend fun updateAlert(alert: TradeAlert) {
        database.tradeAlertDao().updateAlert(alert)
    }

    suspend fun deleteAlert(alert: TradeAlert) {
        database.tradeAlertDao().deleteAlert(alert)
    }

    suspend fun deleteAlertById(id: Long) {
        database.tradeAlertDao().deleteAlertById(id)
    }

    suspend fun toggleAlertStatus(id: Long, isActive: Boolean) {
        database.tradeAlertDao().toggleAlertStatus(id, isActive)
    }

    suspend fun updateAlertTriggered(id: Long, timestamp: Long) {
        database.tradeAlertDao().updateLastTriggered(id, timestamp)
    }

    // Trade Notifications
    val allNotifications: Flow<List<TradeNotification>> = database.tradeNotificationDao().getAllNotifications()
    val unreadCount: Flow<Int> = database.tradeNotificationDao().getUnreadCount()

    suspend fun insertNotification(notification: TradeNotification): Long {
        return database.tradeNotificationDao().insertNotification(notification)
    }

    suspend fun markNotificationAsRead(id: Long) {
        database.tradeNotificationDao().markAsRead(id)
    }

    suspend fun markAllNotificationsAsRead() {
        database.tradeNotificationDao().markAllAsRead()
    }

    suspend fun deleteNotification(id: Long) {
        database.tradeNotificationDao().deleteNotification(id)
    }

    suspend fun clearAllNotifications() {
        database.tradeNotificationDao().clearAllNotifications()
    }

    // Fleet Missions
    val allMissions: Flow<List<FleetMission>> = database.fleetMissionDao().getAllMissions()

    suspend fun insertMission(mission: FleetMission): Long {
        return database.fleetMissionDao().insertMission(mission)
    }

    suspend fun updateMissionStatus(id: Long, status: String) {
        database.fleetMissionDao().updateStatus(id, status)
    }

    suspend fun deleteMission(id: Long) {
        database.fleetMissionDao().deleteMission(id)
    }

    // Tycoon Notes
    val allNotes: Flow<List<TycoonNote>> = database.tycoonNoteDao().getAllNotes()

    suspend fun insertNote(note: TycoonNote): Long {
        return database.tycoonNoteDao().insertNote(note)
    }

    suspend fun deleteNote(note: TycoonNote) {
        database.tycoonNoteDao().deleteNote(note)
    }

    suspend fun deleteNoteById(id: Long) {
        database.tycoonNoteDao().deleteNoteById(id)
    }
}
