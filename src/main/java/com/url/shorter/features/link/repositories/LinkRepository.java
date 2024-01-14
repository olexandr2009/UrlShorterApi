package com.url.shorter.features.link.repositories;

import com.url.shorter.features.link.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring repository for LinkEntity
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, String> {
}
