package com.example.model

data class ProductionRecipe(
    val id: String,
    val outputName: String,
    val outputQuantity: Int,
    val outputUnit: String,
    val facilityRequired: String,
    val cycleTimeMinutes: Int,
    val energyConsumedKWh: Int,
    val inputs: List<Pair<String, Int>>,
    val estimatedProfitPerHourDollars: Double
)

object GalacticProductionData {
    fun getRecipes(): List<ProductionRecipe> {
        return listOf(
            ProductionRecipe(
                id = "rec_iron_smelting",
                outputName = "Iron",
                outputQuantity = 1,
                outputUnit = "unit",
                facilityRequired = "Smelter",
                cycleTimeMinutes = 60,
                energyConsumedKWh = 80,
                inputs = listOf("Iron Ore" to 10),
                estimatedProfitPerHourDollars = 15.00
            ),
            ProductionRecipe(
                id = "rec_copper_smelting",
                outputName = "Copper",
                outputQuantity = 1,
                outputUnit = "unit",
                facilityRequired = "Smelter",
                cycleTimeMinutes = 90,
                energyConsumedKWh = 120,
                inputs = listOf("Copper Ore" to 12),
                estimatedProfitPerHourDollars = 23.00
            ),
            ProductionRecipe(
                id = "rec_concrete_mixing",
                outputName = "Concrete",
                outputQuantity = 5,
                outputUnit = "units",
                facilityRequired = "Chemical Plant",
                cycleTimeMinutes = 45,
                energyConsumedKWh = 90,
                inputs = listOf("Silica" to 8, "Water" to 4),
                estimatedProfitPerHourDollars = 18.50
            ),
            ProductionRecipe(
                id = "rec_rations_prep",
                outputName = "Rations",
                outputQuantity = 10,
                outputUnit = "packs",
                facilityRequired = "Food Processing Plant",
                cycleTimeMinutes = 30,
                energyConsumedKWh = 50,
                inputs = listOf("Grain" to 15, "Drinking Water" to 5),
                estimatedProfitPerHourDollars = 25.00
            ),
            ProductionRecipe(
                id = "rec_robotics",
                outputName = "Robot",
                outputQuantity = 1,
                outputUnit = "unit",
                facilityRequired = "Robotics Factory",
                cycleTimeMinutes = 180,
                energyConsumedKWh = 450,
                inputs = listOf("Iron" to 5, "Copper" to 2, "Tools" to 4),
                estimatedProfitPerHourDollars = 85.00
            ),
            ProductionRecipe(
                id = "rec_construction_kit",
                outputName = "Construction Kit",
                outputQuantity = 1,
                outputUnit = "kit",
                facilityRequired = "Manufacturing Plant",
                cycleTimeMinutes = 120,
                energyConsumedKWh = 220,
                inputs = listOf("Concrete" to 10, "Iron" to 4, "Tools" to 2),
                estimatedProfitPerHourDollars = 42.00
            )
        )
    }
}
