package com.aditya.urlshort.dto;

public record CreateShortUrlResponse(

        String shortCode,
        String shortUrl,
        String longUrl

) {
}