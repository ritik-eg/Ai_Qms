package com.emergency.repository;

import com.emergency.entity.EmergencyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyGroupRepository extends JpaRepository<EmergencyGroup, Long>, JpaSpecificationExecutor<EmergencyGroup> {

    Optional<EmergencyGroup> findByIdAndDeletedFalse(Long id);

    Page<EmergencyGroup> findByDeletedFalse(Pageable pageable);

    @Query("SELECT eg FROM EmergencyGroup eg WHERE eg.deleted = false AND " +
           "(LOWER(eg.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(eg.number) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(eg.type) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<EmergencyGroup> searchGroups(@Param("search") String search, Pageable pageable);

    @Query("SELECT eg FROM EmergencyGroup eg WHERE eg.deleted = false AND eg.type = :type")
    Page<EmergencyGroup> findByType(@Param("type") String type, Pageable pageable);

    boolean existsByNumberAndDeletedFalse(String number);

    @Query("SELECT COUNT(eg) FROM EmergencyGroup eg WHERE eg.deleted = false")
    long countActiveGroups();
}
