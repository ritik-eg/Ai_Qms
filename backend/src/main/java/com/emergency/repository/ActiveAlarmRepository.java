package com.emergency.repository;

import com.emergency.entity.ActiveAlarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveAlarmRepository extends JpaRepository<ActiveAlarm, Long> {

    @Query("SELECT aa FROM ActiveAlarm aa WHERE aa.status = 'ACTIVE'")
    Page<ActiveAlarm> findAllActiveAlarms(Pageable pageable);

    @Query("SELECT aa FROM ActiveAlarm aa WHERE aa.emergencyGroup.id = :groupId")
    Page<ActiveAlarm> findByEmergencyGroupId(@Param("groupId") Long groupId, Pageable pageable);

    @Query("SELECT aa FROM ActiveAlarm aa WHERE aa.emergencyGroup.id = :groupId AND aa.status = 'ACTIVE'")
    Optional<ActiveAlarm> findActiveAlarmByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT COUNT(aa) FROM ActiveAlarm aa WHERE aa.status = 'ACTIVE'")
    long countActiveAlarms();

    @Query("SELECT COUNT(aa) FROM ActiveAlarm aa")
    long countTotalActivations();
}
