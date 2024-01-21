package com.url.shorter.features.link.repositories;

import com.url.shorter.features.link.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring repository for LinkEntity.
 * JPA-query for custom methods is created automatically from the method's name.
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, UUID> {
    Optional<LinkEntity> findByLongLink(String longLink);
    void deleteByLongLink(String longLink); // is not needed??
    Optional<LinkEntity> findByShortLink(String  ShortLink);

}