package com.emergency.repository;

import com.emergency.entity.AlarmHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {

    @Query("SELECT ah FROM AlarmHistory ah WHERE ah.emergencyGroup.id = :groupId ORDER BY ah.actionDate DESC")
    List<AlarmHistory> findByEmergencyGroupId(@Param("groupId") Long groupId);

    @Query("SELECT ah FROM AlarmHistory ah WHERE " +
           "(:groupId IS NULL OR ah.emergencyGroup.id = :groupId) AND " +
           "(:startDate IS NULL OR ah.actionDate >= :startDate) AND " +
           "(:endDate IS NULL OR ah.actionDate <= :endDate) " +
           "ORDER BY ah.actionDate DESC")
    Page<AlarmHistory> findWithFilters(
            @Param("groupId") Long groupId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
