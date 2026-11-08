package com.roshni.livemoreapi.dto.response;

import com.roshni.livemoreapi.entity.TicketTier;
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
public class TicketTierResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer capacity;
    private Integer soldCount;
    private Boolean isActive;
    private Integer maxPerOrder;
    private Integer displayOrder;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TicketTierResponse fromEntity(TicketTier tier) {
        if (tier == null) {
            return null;
        }
        return TicketTierResponse.builder()
                .id(tier.getId())
                .name(tier.getName())
                .description(tier.getDescription())
                .price(tier.getPrice())
                .capacity(tier.getCapacity())
                .soldCount(tier.getSoldCount())
                .isActive(tier.getIsActive())
                .maxPerOrder(tier.getMaxPerOrder())
                .displayOrder(tier.getDisplayOrder())
                .bookingStartTime(tier.getBookingStartTime())
                .bookingEndTime(tier.getBookingEndTime())
                .createdAt(tier.getCreatedAt())
                .updatedAt(tier.getUpdatedAt())
                .build();
    }
}
