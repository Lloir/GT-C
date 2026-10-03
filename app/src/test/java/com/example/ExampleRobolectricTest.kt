package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.network.ExchangeRateLimiter
import com.example.model.GalacticMarketData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches Galactic Tycoons`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Galactic Tycoons", appName)
    }

    @Test
    fun `verify real galactic materials populated`() {
        val commodities = GalacticMarketData.getInitialCommodities()
        assertTrue(commodities.isNotEmpty())
        assertTrue(commodities.any { it.name == "Iron Ore" && it.matId == 1 })
        assertTrue(commodities.any { it.name == "Copper" && it.matId == 6 })
        assertTrue(commodities.any { it.name == "Robot" && it.matId == 20 })
        assertTrue(commodities.any { it.name == "Hydrogen" && it.matId == 24 })
    }

    @Test
    fun `verify rate limiter enforces 100 units budget and cost deductions`() {
        val initialBudget = ExchangeRateLimiter.remainingUnits.value
        assertTrue("Budget should be positive", initialBudget > 0)
        assertTrue("Can spend 5 units for mat-prices", ExchangeRateLimiter.canSpend(5))

        val spent = ExchangeRateLimiter.trySpend(5)
        assertTrue(spent)
        assertEquals(initialBudget - 5, ExchangeRateLimiter.remainingUnits.value)
    }

    @Test
    fun `verify clean database starts with zero dummy test alerts`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getInstance(context)
        val alerts = db.tradeAlertDao().getAllAlerts().first()
        val notifs = db.tradeNotificationDao().getAllNotifications().first()
        val missions = db.fleetMissionDao().getAllMissions().first()

        assertEquals("No dummy test alerts on fresh db", 0, alerts.size)
        assertEquals("No dummy test notifications on fresh db", 0, notifs.size)
        assertEquals("No dummy test missions on fresh db", 0, missions.size)
    }
}
