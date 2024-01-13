package com.url.shorter.link.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring repository for link
 */
@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, String> {
}
