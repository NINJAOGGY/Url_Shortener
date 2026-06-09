package com.aditya.urlshort.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlRequest(

        @NotBlank(message = "URL cannot be empty")
        String longUrl,
        String customAlias

) {
}   