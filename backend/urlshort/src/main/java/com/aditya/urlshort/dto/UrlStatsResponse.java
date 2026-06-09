package com.aditya.urlshort.dto;

import java.time.LocalDateTime;

public record UrlStatsResponse(
        String shortCode,
        String longUrl,
        Long clickCount,
        LocalDateTime createdAt
) {
}