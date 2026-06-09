package com.aditya.urlshort.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.aditya.urlshort.dto.CreateShortUrlRequest;
import com.aditya.urlshort.dto.CreateShortUrlResponse;
import com.aditya.urlshort.dto.UrlStatsResponse;
import com.aditya.urlshort.entity.Url;
import com.aditya.urlshort.repository.UrlRepository;
import com.aditya.urlshort.exception.AliasAlreadyExistsException;
import com.aditya.urlshort.exception.ShortUrlNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.StringRedisTemplate;

@Service
public class UrlService {

    private final StringRedisTemplate redisTemplate;

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int SHORT_CODE_LENGTH = 6;

    private final UrlRepository urlRepository;

    private final SecureRandom random = new SecureRandom();

    public UrlService(UrlRepository urlRepository, StringRedisTemplate redisTemplate) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public CreateShortUrlResponse createShortUrl(
            CreateShortUrlRequest request) {

        String shortCode;

        if (request.customAlias() != null
                && !request.customAlias().isBlank()) {

            if (urlRepository.existsByShortCode(
                    request.customAlias())) {

                throw new AliasAlreadyExistsException(
                        request.customAlias()
                );
            }

            shortCode = request.customAlias();

        } else {

            shortCode = generateUniqueShortCode();
        }

        Url url = new Url();
        url.setShortCode(shortCode);
        url.setLongUrl(request.longUrl());

        Url savedUrl = urlRepository.save(url);

        return new CreateShortUrlResponse(
                savedUrl.getShortCode(),
                "http://localhost:8080/" + savedUrl.getShortCode(),
                savedUrl.getLongUrl()
        );
    }

    private String generateUniqueShortCode() {

        String shortCode;

        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    private String generateShortCode() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(
                    BASE62.charAt(
                            random.nextInt(BASE62.length())
                    )
            );
        }

        return sb.toString();
    }

    @Transactional
    public String getLongUrl(String shortCode) {

        String cacheKey = "url:" + shortCode;

        String cachedLongUrl =
                redisTemplate.opsForValue()
                        .get(cacheKey);

        if (cachedLongUrl != null) {

            System.out.println(
                    "CACHE HIT: " + shortCode
            );

            redisTemplate.opsForValue()
        .increment("click:" + shortCode);

            return cachedLongUrl;
        }

        System.out.println(
                "CACHE MISS: " + shortCode
        );

        Url url = urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(
                        () -> new ShortUrlNotFoundException(shortCode)
                );

        redisTemplate.opsForValue()
                .set(
                        cacheKey,
                        url.getLongUrl()
                );

        redisTemplate.opsForValue()
        .increment("click:" + shortCode);

        return url.getLongUrl();

    }

    public UrlStatsResponse getUrlStats(String shortCode) {

            Url url = urlRepository
                    .findByShortCode(shortCode)
                    .orElseThrow(
                            () -> new ShortUrlNotFoundException(shortCode)
                    );

            return new UrlStatsResponse(
                    url.getShortCode(),
                    url.getLongUrl(),
                    url.getClickCount(),
                    url.getCreatedAt()
            );
    }

    @Transactional
    public void deleteUrl(String shortCode) {

        urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(
                        () -> new ShortUrlNotFoundException(shortCode)
                );

        urlRepository.deleteByShortCode(shortCode);

        redisTemplate.delete(
                "url:" + shortCode
        );
    }

    // public void testRedis() {

    //     redisTemplate.opsForValue().set("hello", "world");
    //     String value = redisTemplate.opsForValue().get("hello");
    //     System.out.println("Value from Redis: " + value);
    // }
}