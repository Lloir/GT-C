package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GalacticProductionData
import com.example.model.ProductionRecipe
import com.example.ui.components.SciFiCard
import com.example.ui.theme.AlertAmber
import com.example.ui.theme.CyanElectric
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.LossRed
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.SpaceCardBg
import com.example.ui.theme.SpaceCardBorder
import com.example.ui.theme.SpaceDark
import com.example.ui.theme.SpaceSurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.GalacticTycoonsViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndustryCalculatorScreen(
    viewModel: GalacticTycoonsViewModel
) {
    val commodities by viewModel.commodities.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    // Trade Margin Calculator Inputs
    var selectedCommodity by remember { mutableStateOf(commodities.firstOrNull()) }
    var cargoTonsInput by remember { mutableStateOf("1000") }
    var buyPriceInput by remember { mutableStateOf(selectedCommodity?.currentPrice?.let { String.format(Locale.US, "%.2f", it * 0.90) } ?: "20.00") }
    var sellPriceInput by remember { mutableStateOf(selectedCommodity?.currentPrice?.let { String.format(Locale.US, "%.2f", it) } ?: "23.00") }
    var fuelCostInput by remember { mutableStateOf("150.0") }
    var brokerFeePctInput by remember { mutableStateOf("3.5") }
    var isCommodityDropdownExpanded by remember { mutableStateOf(false) }

    // Calculation results
    val tons = cargoTonsInput.toDoubleOrNull() ?: 0.0
    val buyPrice = buyPriceInput.toDoubleOrNull() ?: 0.0
    val sellPrice = sellPriceInput.toDoubleOrNull() ?: 0.0
    val fuelCost = fuelCostInput.toDoubleOrNull() ?: 0.0
    val brokerFeePct = brokerFeePctInput.toDoubleOrNull() ?: 0.0

    val grossRevenue = tons * sellPrice
    val totalPurchaseCost = tons * buyPrice
    val brokerFee = grossRevenue * (brokerFeePct / 100.0)
    val totalCost = totalPurchaseCost + fuelCost + brokerFee
    val netProfit = grossRevenue - totalCost
    val roiPercent = if (totalCost > 0) (netProfit / totalCost) * 100.0 else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "INDUSTRY & CALCULATOR",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Cargo Arbitrage Profit & Production Chains",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SpaceCardBg,
            contentColor = CyanElectric,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = CyanElectric
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Trade Profit Calculator", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Production Recipes", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedTab) {
            0 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        // Profit Summary Result Box
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SpaceCardBg,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (netProfit >= 0) ProfitGreen.copy(alpha = 0.6f) else LossRed.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "ESTIMATED NET TRADE RETURN",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val formattedNet = String.format(Locale.US, "%+,.2f $", netProfit)
                                    Text(
                                        text = formattedNet,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (netProfit >= 0) ProfitGreen else LossRed
                                    )

                                    val formattedRoi = String.format(Locale.US, "%+.1f%% ROI", roiPercent)
                                    Surface(
                                        color = (if (netProfit >= 0) ProfitGreen else LossRed).copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = formattedRoi,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (netProfit >= 0) ProfitGreen else LossRed,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("Gross Revenue", fontSize = 10.sp, color = TextMuted)
                                        Text(
                                            String.format(Locale.US, "$%,.2f", grossRevenue),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextPrimary
                                        )
                                    }
                                    Column {
                                        Text("Total Outlay", fontSize = 10.sp, color = TextMuted)
                                        Text(
                                            String.format(Locale.US, "$%,.2f", totalPurchaseCost),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextPrimary
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Fees & Freight", fontSize = 10.sp, color = TextMuted)
                                        Text(
                                            String.format(Locale.US, "$%,.2f", brokerFee + fuelCost),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = LossRed
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        // Calculator Inputs Card
                        SciFiCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "CARGO & RUN PARAMETERS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CyanElectric
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // Select Commodity Dropdown
                                ExposedDropdownMenuBox(
                                    expanded = isCommodityDropdownExpanded,
                                    onExpandedChange = { isCommodityDropdownExpanded = !isCommodityDropdownExpanded },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    OutlinedTextField(
                                        value = selectedCommodity?.name ?: "Select Commodity",
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Commodity") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCommodityDropdownExpanded) },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = CyanElectric,
                                            unfocusedBorderColor = SpaceCardBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        ),
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = isCommodityDropdownExpanded,
                                        onDismissRequest = { isCommodityDropdownExpanded = false },
                                        modifier = Modifier.background(SpaceCardBg)
                                    ) {
                                        commodities.forEach { comm ->
                                            DropdownMenuItem(
                                                text = { Text("${comm.name} (\$${String.format(Locale.US, "%.2f", comm.currentPrice)})", color = TextPrimary) },
                                                onClick = {
                                                    selectedCommodity = comm
                                                    sellPriceInput = String.format(Locale.US, "%.2f", comm.currentPrice)
                                                    buyPriceInput = String.format(Locale.US, "%.2f", comm.currentPrice * 0.90)
                                                    isCommodityDropdownExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = cargoTonsInput,
                                    onValueChange = { cargoTonsInput = it },
                                    label = { Text("Hauler Cargo Units") },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyanElectric,
                                        unfocusedBorderColor = SpaceCardBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedTextField(
                                        value = buyPriceInput,
                                        onValueChange = { buyPriceInput = it },
                                        label = { Text("Buy Price ($/unit)") },
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = CyanElectric,
                                            unfocusedBorderColor = SpaceCardBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )

                                    OutlinedTextField(
                                        value = sellPriceInput,
                                        onValueChange = { sellPriceInput = it },
                                        label = { Text("Sell Price ($/unit)") },
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = CyanElectric,
                                            unfocusedBorderColor = SpaceCardBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedTextField(
                                        value = fuelCostInput,
                                        onValueChange = { fuelCostInput = it },
                                        label = { Text("Fuel Cost ($)") },
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = CyanElectric,
                                            unfocusedBorderColor = SpaceCardBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )

                                    OutlinedTextField(
                                        value = brokerFeePctInput,
                                        onValueChange = { brokerFeePctInput = it },
                                        label = { Text("Exchange Fee %") },
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = CyanElectric,
                                            unfocusedBorderColor = SpaceCardBorder,
                                            focusedTextColor = TextPrimary,
                                            unfocusedTextColor = TextPrimary
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                // Production Recipes Guide
                val recipes = remember { GalacticProductionData.getRecipes() }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(recipes, key = { it.id }) { recipe ->
                        ProductionRecipeCard(recipe = recipe)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductionRecipeCard(recipe: ProductionRecipe) {
    SciFiCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = recipe.outputName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Yield: ${recipe.outputQuantity} ${recipe.outputUnit} • ${recipe.facilityRequired}",
                        fontSize = 11.sp,
                        color = CyanElectric
                    )
                }

                Surface(
                    color = Color(0x2200E676),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    val formatted = String.format(Locale.US, "+$%,.2f/hr", recipe.estimatedProfitPerHourDollars)
                    Text(
                        text = formatted,
                        color = ProfitGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Inputs Required
            Text("INPUTS REQUIRED:", fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                recipe.inputs.forEach { (name, qty) ->
                    Surface(
                        color = SpaceSurfaceLight,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "$name: $qty",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cycle: ${recipe.cycleTimeMinutes} mins",
                    fontSize = 10.sp,
                    color = TextMuted
                )
                Text(
                    text = "Power: ${recipe.energyConsumedKWh} kWh",
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }
        }
    }
}
