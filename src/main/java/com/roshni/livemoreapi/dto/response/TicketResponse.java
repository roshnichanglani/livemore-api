package com.roshni.livemoreapi.dto.response;
import com.roshni.livemoreapi.entity.Ticket;
import com.roshni.livemoreapi.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private String ticketCode;
    private String bookingReference;
    private BigDecimal price;
    private TicketStatus status;
    private LocalDateTime checkedInAt;

    // Flattened relational data for clean API consumption
    private Long tierId;
    private String tierName;
    private Long eventId;
    private String eventTitle;
    private Long userId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TicketResponse fromEntity(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .bookingReference(ticket.getBookingReference())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .checkedInAt(ticket.getCheckedInAt())
                .tierId(ticket.getTicketTier() != null ? ticket.getTicketTier().getId() : null)
                .tierName(ticket.getTicketTier() != null ? ticket.getTicketTier().getName() : null)
                .eventId(ticket.getTicketTier() != null && ticket.getTicketTier().getEvent() != null
                        ? ticket.getTicketTier().getEvent().getId() : null)
                .eventTitle(ticket.getTicketTier() != null && ticket.getTicketTier().getEvent() != null
                        ? ticket.getTicketTier().getEvent().getTitle() : null)
                .userId(ticket.getUser() != null ? ticket.getUser().getId() : null)
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
