package com.aditya.urlshort.scheduler;

import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aditya.urlshort.repository.UrlRepository;

@Component
public class AnalyticsFlushScheduler {

    private final StringRedisTemplate redisTemplate;
    private final UrlRepository urlRepository;

    public AnalyticsFlushScheduler(
            StringRedisTemplate redisTemplate,
            UrlRepository urlRepository) {

        this.redisTemplate = redisTemplate;
        this.urlRepository = urlRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void flushClickCounts() {

        Set<String> keys =
                redisTemplate.keys("click:*");

        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {

            String shortCode =
                    key.replace("click:", "");

            String value =
                    redisTemplate.opsForValue()
                            .get(key);

            if (value == null) {
                continue;
            }

            Long clickCount =
                    Long.parseLong(value);

            urlRepository.incrementClickCountBy(
                    shortCode,
                    clickCount
            );

            redisTemplate.delete(key);

            System.out.println(
                    "Flushed "
                            + clickCount
                            + " clicks for "
                            + shortCode
            );
        }
    }
}