package com.url.shorter.features.link.repositories;

import com.url.shorter.features.link.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring repository for LinkEntity
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, UUID> {
    Optional<LinkEntity> findByLongLink(String longLink);
    void deleteByLongLink(String longLink);
}
