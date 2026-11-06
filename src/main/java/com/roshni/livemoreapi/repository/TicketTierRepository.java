package com.roshni.livemoreapi.repository;

import com.roshni.livemoreapi.entity.TicketTier;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketTierRepository extends JpaRepository<TicketTier, Long> {

    // 1. Find all ticket tiers belonging to a specific event ID
    List<TicketTier> findByEventId(Long eventId);

    // 2. Find all ticket tiers belonging to a specific event slug (Great for event detail page rendering)
    List<TicketTier> findByEvent_Slug(String slug);

    // 3. Find a specific ticket tier with PESSIMISTIC_WRITE lock
    // Crucial for preventing race conditions and overselling during high-concurrency ticket checkouts!
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from TicketTier t where t.id = :id")
    Optional<TicketTier> findByIdWithLock(@Param("id") Long id);
}
