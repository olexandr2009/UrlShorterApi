package com.url.shorter.features.link.repositories;

import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring repository for LinkEntity
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, UUID> {
    Optional<LinkEntity> findByLongLink(String longLink);
    void deleteByLongLink(String longLink);
    List<LinkEntity> findByUserId(UserEntity userEntity);
}