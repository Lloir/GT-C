package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.network.PShipModel
import com.example.ui.components.SciFiCard
import com.example.ui.theme.AlertAmber
import com.example.ui.theme.CyanElectric
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.LossRed
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.SpaceCardBg
import com.example.ui.theme.SpaceCardBorder
import com.example.ui.theme.SpaceSurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.GalacticTycoonsViewModel
import java.util.Locale

@Composable
fun FleetLogisticsScreen(
    viewModel: GalacticTycoonsViewModel
) {
    val isApiKeyConfigured by viewModel.isApiKeyConfigured.collectAsState()
    val companyData by viewModel.companyData.collectAsState()
    val isLoadingCompany by viewModel.isLoadingCompany.collectAsState()
    val companyError by viewModel.companyError.collectAsState()

    var apiKeyInput by remember { mutableStateOf("") }
    var isKeyVisible by remember { mutableStateOf(false) }
    var showDisconnectConfirmation by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Screen Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "FLEET LOGISTICS",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Company Starship Telemetry & Operations",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                if (isApiKeyConfigured) {
                    IconButton(
                        onClick = { viewModel.fetchCompanyFleet() },
                        modifier = Modifier
                            .size(36.dp)
                            .background(SpaceSurfaceLight, RoundedCornerShape(10.dp))
                            .testTag("refresh_fleet_button")
                    ) {
                        if (isLoadingCompany) {
                            CircularProgressIndicator(
                                color = CyanElectric,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh Fleet Telemetry",
                                tint = CyanElectric,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Lazy Column for Fleet content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ==========================================
                // API KEY CONFIGURATION SECTION
                // ==========================================
                if (!isApiKeyConfigured) {
                    item {
                        // Explanation & Input card (VISIBLE WHEN NOT ADDED)
                        SciFiCard(
                            borderColor = CyanElectric,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0x3300E5FF),
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.Key,
                                                contentDescription = null,
                                                tint = CyanElectric,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "COMPANY API KEY REQUIRED",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = CyanElectric,
                                            letterSpacing = 0.5.sp
                                        )
                                        Text(
                                            text = "Authentication for /public/company telemetry",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "To track and command your company's real starships, Galactic Tycoons requires your personal Company API Key.\n\n" +
                                            "1. Open the game at g2.galactictycoons.com\n" +
                                            "2. Go to in-game Settings ➔ API Keys\n" +
                                            "3. Create a key with 'Limited' or 'Extended' access and paste it below.",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                // Input Field
                                OutlinedTextField(
                                    value = apiKeyInput,
                                    onValueChange = { apiKeyInput = it },
                                    label = { Text("Company API Key") },
                                    placeholder = { Text("Paste your Company API key here...", color = TextMuted) },
                                    singleLine = true,
                                    visualTransformation = if (isKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        IconButton(onClick = { isKeyVisible = !isKeyVisible }) {
                                            Icon(
                                                imageVector = if (isKeyVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                contentDescription = if (isKeyVisible) "Hide Key" else "Show Key",
                                                tint = TextSecondary
                                            )
                                        }
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyanElectric,
                                        unfocusedBorderColor = SpaceCardBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("company_api_key_input")
                                )

                                if (companyError != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = companyError ?: "",
                                        color = LossRed,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        if (apiKeyInput.isNotBlank()) {
                                            viewModel.saveApiKey(apiKeyInput)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = CyanElectric),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .testTag("save_api_key_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Connect Company API Key", color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    // ==========================================
                    // CONNECTED STATUS BANNER (INPUT FIELD IS HIDDEN)
                    // ==========================================
                    item {
                        SciFiCard(
                            borderColor = ProfitGreen.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = CircleShape,
                                            color = Color(0x3300E676),
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = ProfitGreen,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = companyData?.name ?: "COMPANY API CONNECTED",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = ProfitGreen
                                            )
                                            val prStr = companyData?.pr?.let { " • Prestige: $it" } ?: ""
                                            val rankStr = companyData?.rank?.let { " • Rank: #$it" } ?: ""
                                            Text(
                                                text = "Authenticated with Galactic Tycoons$prStr$rankStr",
                                                fontSize = 11.sp,
                                                color = TextSecondary
                                            )
                                        }
                                    }

                                    OutlinedButton(
                                        onClick = { showDisconnectConfirmation = true },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("Disconnect", fontSize = 11.sp, color = TextSecondary)
                                    }
                                }

                                if (companyData?.cash != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    val cashDollars = (companyData?.cash ?: 0) / 100.0
                                    Text(
                                        text = String.format(Locale.US, "Company Treasury: $%,.2f", cashDollars),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = GoldAccent
                                    )
                                }
                            }
                        }
                    }

                    // Disconnect Confirmation Dialog
                    if (showDisconnectConfirmation) {
                        item {
                            Surface(
                                color = SpaceSurfaceLight,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Disconnect Company API Key?", fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text("This will remove your stored Company API key and stop fleet telemetry.", fontSize = 11.sp, color = TextSecondary)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = {
                                                viewModel.clearApiKey()
                                                showDisconnectConfirmation = false
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = LossRed),
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text("Confirm Disconnect", fontSize = 11.sp, color = Color.White)
                                        }
                                        OutlinedButton(
                                            onClick = { showDisconnectConfirmation = false },
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.height(32.dp)
                                        ) {
                                            Text("Cancel", fontSize = 11.sp, color = TextSecondary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // ==========================================
                // REAL COMPANY FLEET SHIPS LIST
                // ==========================================
                val realShips = companyData?.ships ?: emptyList()

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COMPANY FLEET (${realShips.size} SHIPS)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        if (isApiKeyConfigured) {
                            Text(
                                text = "Live Server Telemetry",
                                fontSize = 11.sp,
                                color = CyanElectric
                            )
                        }
                    }
                }

                if (isLoadingCompany && realShips.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = CyanElectric)
                        }
                    }
                } else if (!isApiKeyConfigured) {
                    item {
                        SciFiCard(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier.padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.LocalShipping,
                                        contentDescription = null,
                                        tint = TextMuted,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Connect your API key above to view active ships and flights.",
                                        fontSize = 12.sp,
                                        color = TextSecondary,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                } else if (realShips.isEmpty()) {
                    item {
                        SciFiCard(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier.padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No ships currently registered under your company. Build or purchase starships in-game to view telemetry here.",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(realShips, key = { it.id }) { ship ->
                        ShipTelemetryCard(ship = ship)
                    }
                }
            }
        }
    }
}

@Composable
fun ShipTelemetryCard(ship: PShipModel) {
    val isInFlight = ship.flight != null && (ship.flight.destPId != null || ship.flight.aDate != null)
    val conditionPct = ((ship.condition ?: 1.0f) * 100).toInt()
    val fuelVal = ship.fuel ?: 0f

    SciFiCard(
        borderColor = if (isInFlight) CyanElectric.copy(alpha = 0.6f) else SpaceCardBorder,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ship_card_${ship.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Ship Name and Flight Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isInFlight) Icons.Default.RocketLaunch else Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = if (isInFlight) CyanElectric else GoldAccent,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = ship.name.ifBlank { "Ship #${ship.id}" },
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = if (ship.pId != null) "Stationed: Planet #${ship.pId}" else "Deep Space Orbit",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isInFlight) Color(0x3300E5FF) else SpaceSurfaceLight
                ) {
                    Text(
                        text = if (isInFlight) "IN TRANSIT" else "DOCKED",
                        color = if (isInFlight) CyanGlow else TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Ship Diagnostics: Condition & Fuel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Hull condition
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = ProfitGreen, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Hull Integrity", fontSize = 10.sp, color = TextMuted)
                        }
                        Text("$conditionPct%", fontSize = 10.sp, color = ProfitGreen, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    LinearProgressIndicator(
                        progress = { (ship.condition ?: 1.0f).coerceIn(0f, 1f) },
                        color = if (conditionPct > 50) ProfitGreen else LossRed,
                        trackColor = SpaceSurfaceLight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                    )
                }

                // Fuel
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalGasStation, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Propellant", fontSize = 10.sp, color = TextMuted)
                        }
                        Text(String.format(Locale.US, "%.1f", fuelVal), fontSize = 10.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    LinearProgressIndicator(
                        progress = { (fuelVal / 100f).coerceIn(0.05f, 1f) },
                        color = GoldAccent,
                        trackColor = SpaceSurfaceLight,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                    )
                }
            }

            // In-Flight Trajectory Details
            if (isInFlight && ship.flight != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = SpaceSurfaceLight,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = CyanElectric, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "En route to Planet #${ship.flight.destPId ?: "Sector"}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        if (ship.flight.aDate != null) {
                            Text(
                                text = "Arrival: ${ship.flight.aDate}",
                                fontSize = 10.sp,
                                color = CyanGlow
                            )
                        }
                    }
                }
            }
        }
    }
}
