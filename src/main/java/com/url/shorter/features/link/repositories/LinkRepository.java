package com.url.shorter.features.link.repositories;

import com.url.shorter.features.link.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring repository for LinkEntity.
 * JPA-query for custom methods is created automatically from the method's name.
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, UUID> {
    Optional<LinkEntity> findByLongLink(String longLink);
    Optional<LinkEntity> findByShortLink(String ShortLink);
    void deleteByLongLink(String longLink);
    List<LinkEntity> findByUserId(UUID userId);
    boolean existsByShortLink(String shortLink);

    @Modifying
    @Query(value = "update LinkEntity le set le.clicks = le.clicks + 1 where le.id = :id")
    void updateUsedCount(@Param(value = "id") UUID id);
}