package com.url.shorter.features.user.repositories;

import com.url.shorter.features.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("FROM UserEntity ue WHERE ue.username IN :names")
    List<UserEntity> findByUsernames(@Param("names") Collection<String> names);

}