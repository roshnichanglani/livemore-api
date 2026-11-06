package com.roshni.livemoreapi.repository;

import com.roshni.livemoreapi.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketCode(String ticketCode);

    boolean existsByTicketCode(String ticketCode);

    // 3. Find all tickets grouped under a specific booking reference
    // This replaces the need for a heavy Order entity when fetching a user's purchase group!
    List<Ticket> findByBookingReference(String bookingReference);

    // 4. Find all tickets belonging to a specific event (Paged for admin dashboards & analytics)
    Page<Ticket> findByTicketTier_EventId(Long eventId, Pageable pageable);

    // 5. Find all tickets belonging to a specific ticket tier (Paged)
    Page<Ticket> findByTicketTierId(Long tierId, Pageable pageable);

    // Count how many tickets have been sold for a specific ticket tier
    long countByTicketTierId(Long tierId);

    // Count how many tickets have been sold across an entire event
    // (traversing Ticket -> TicketTier -> Event ID)
    long countByTicketTier_EventId(Long eventId);
}
