package com.example.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatPriceDto(
    @Json(name = "matId") val matId: Int,
    @Json(name = "matName") val matName: String,
    @Json(name = "currentPrice") val currentPrice: Long, // in cents (e.g. 2300 = $23.00)
    @Json(name = "avgPrice") val avgPrice: Long // in cents
)

@JsonClass(generateAdapter = true)
data class MatPricesResponse(
    @Json(name = "prices") val prices: List<MatPriceDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ExchangeOrderDto(
    @Json(name = "id") val id: Long,
    @Json(name = "cId") val cId: Long? = null,
    @Json(name = "cName") val cName: String,
    @Json(name = "unitPrice") val unitPrice: Long, // in cents
    @Json(name = "qty") val qty: Long
)

@JsonClass(generateAdapter = true)
data class MatDetailsResponse(
    @Json(name = "matId") val matId: Int,
    @Json(name = "matName") val matName: String,
    @Json(name = "currentPrice") val currentPrice: Long,
    @Json(name = "avgPrice") val avgPrice: Long,
    @Json(name = "totalQtyAvailable") val totalQtyAvailable: Long = 0,
    @Json(name = "orders") val orders: List<ExchangeOrderDto> = emptyList()
)

// Company & Fleet Telemetry Models (Requires Player API Key)
@JsonClass(generateAdapter = true)
data class PShipFlightModel(
    @Json(name = "destPId") val destPId: Int? = null,
    @Json(name = "sDate") val sDate: String? = null,
    @Json(name = "aDate") val aDate: String? = null,
    @Json(name = "startFuel") val startFuel: Float? = null,
    @Json(name = "arrivalFuel") val arrivalFuel: Float? = null,
    @Json(name = "type") val type: Int? = null
)

@JsonClass(generateAdapter = true)
data class PShipModel(
    @Json(name = "id") val id: Int,
    @Json(name = "cId") val cId: Int? = null,
    @Json(name = "name") val name: String,
    @Json(name = "fuel") val fuel: Float? = null,
    @Json(name = "condition") val condition: Float? = null,
    @Json(name = "pId") val pId: Int? = null,
    @Json(name = "warehouseId") val warehouseId: Int? = null,
    @Json(name = "flight") val flight: PShipFlightModel? = null
)

@JsonClass(generateAdapter = true)
data class PBasePreviewModel(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "pId") val pId: Int? = null
)

@JsonClass(generateAdapter = true)
data class PMyCompanyModel(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "cash") val cash: Long? = 0, // cents
    @Json(name = "pr") val pr: Int? = 0,
    @Json(name = "rank") val rank: Int? = null,
    @Json(name = "bases") val bases: List<PBasePreviewModel> = emptyList(),
    @Json(name = "ships") val ships: List<PShipModel> = emptyList()
)
