package com.roshni.livemoreapi.repository;

import com.roshni.livemoreapi.entity.Event;
import com.roshni.livemoreapi.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findBySlug(String slug);

    Boolean existsBySlug(String slug);

    Optional<Event> findByTitle(String title);

    Boolean existsByTitle(String title);

    // Find all events belonging to a specific category ID (Paged)
    Page<Event> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Event> findByCategorySlug(String categorySlug, Pageable pageable);

    // Find events by status with pagination
    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    // Full-text search for events by title or description
    @Query("SELECT e FROM Event e WHERE lower(e.title) like lower(concat('%', :searchTerm, '%') ) OR lower(e.description) like lower(concat('%', :searchTerm, '%') )")
    Page<Event> searchEvents(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Find upcoming events starting after a specific date filtered by status
    @Query("SELECT e FROM Event e WHERE e.status = :status AND e.startTime > :now ORDER BY e.startTime ASC")
    Page<Event> findUpcomingEvents(@Param("status") EventStatus status, @Param("now")LocalDateTime now, Pageable pageable);
}
