package com.aditya.urlshort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aditya.urlshort.dto.CreateShortUrlRequest;
import com.aditya.urlshort.dto.CreateShortUrlResponse;
import com.aditya.urlshort.dto.UrlStatsResponse;
import com.aditya.urlshort.service.UrlService;

// import java.net.URI;

// import org.springframework.http.HttpHeaders;
// import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateShortUrlResponse createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request) {

        return urlService.createShortUrl(request);
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlStatsResponse> getStats(
            @PathVariable String shortCode) {

        return ResponseEntity.ok(
                urlService.getUrlStats(shortCode)
        );
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(
            @PathVariable String shortCode) {

        urlService.deleteUrl(shortCode);

        return ResponseEntity.noContent().build();
    }
    // @GetMapping("/{shortCode}")
    // public ResponseEntity<Void> redirectToOriginalUrl(
    //         @PathVariable String shortCode) {

    //     String longUrl = urlService.getLongUrl(shortCode);

    //     return ResponseEntity
    //             .status(302)
    //             .header(HttpHeaders.LOCATION, longUrl)
    //             .build();
    // }
}