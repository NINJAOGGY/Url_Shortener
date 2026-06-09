package com.aditya.urlshort.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.urlshort.entity.Url;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    // @Modifying
    // @Query("""
    //     update Url u
    //     set u.clickCount = u.clickCount + 1
    //     where u.shortCode = :shortCode
    // """)
    // void incrementClickCount(
    //         @Param("shortCode") String shortCode
    // );

    @Modifying
    @Query("""
        update Url u
        set u.clickCount = u.clickCount + :count
        where u.shortCode = :shortCode
    """)
    void incrementClickCountBy(
            @Param("shortCode") String shortCode,
            @Param("count") Long count
    );

    void deleteByShortCode(String shortCode);
}