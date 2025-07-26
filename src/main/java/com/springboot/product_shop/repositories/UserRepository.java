package com.springboot.product_shop.repositories;

import com.springboot.product_shop.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByDocument(String document);

    boolean existsByUsername(String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByDocumentAndIdNot(String document, UUID id);

    boolean existsByUsernameAndIdNot(String username, UUID id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);

    Optional<UserEntity> findByDocument(String document);

    Optional<UserEntity> findByUsername(String username);

    Page<UserEntity> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    Page<UserEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

}
