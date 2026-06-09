package com.aditya.urlshort.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.urlshort.service.UrlService;

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode) {

        String longUrl = urlService.getLongUrl(shortCode);

        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION, longUrl)
                .build();
    }
}