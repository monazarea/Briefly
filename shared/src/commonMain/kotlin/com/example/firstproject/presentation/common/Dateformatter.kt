package com.example.firstproject.presentation.common

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Instant



/**
 * Formats NewsAPI's ISO-8601 `publishedAt` (e.g. "2026-06-20T10:15:00Z")
 * into a short, readable date for list items, e.g. "Jun 20, 2026".
 * Falls back to the raw string if parsing fails so the UI never crashes
 * on an unexpected date format.
 */
fun formatPublishedAt(isoDate: String): String {
    return try {
        val instant = Instant.parse(isoDate)
        val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val month = MONTH_NAMES[local.monthNumber - 1]
        "$month ${local.dayOfMonth}, ${local.year}"
    } catch (e: Exception) {
        isoDate
    }
}

private val MONTH_NAMES = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)