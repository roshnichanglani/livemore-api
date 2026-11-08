package com.roshni.livemoreapi.dto.request;


import jakarta.validation.constraints.*;
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
public class TicketTierRequest {
    @NotBlank(message = "Tier name is required")
    @Size(max = 100, message = "Tier name must not exceed 100 characters")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than zero")
    private Integer capacity;

    private Integer maxPerOrder;

    private Integer displayOrder;

    private LocalDateTime bookingStartTime;

    private LocalDateTime bookingEndTime;
}
