package com.roshni.livemoreapi.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketBookingRequest {

    @NotEmpty(message = "Booking cart must contain at least one ticket selection")
    private List<TicketItemRequest> items;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TicketItemRequest {
        @NotNull(message = "Ticket tier ID is required")
        private Long tierId;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        private Integer quantity;
    }
}
