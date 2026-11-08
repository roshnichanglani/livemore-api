package com.roshni.livemoreapi.dto.request;

import com.roshni.livemoreapi.enums.EventStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String thingsToKnow;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Venue name is required")
    private String venueName;

    @NotBlank(message = "Location/City is required")
    private String location;

    @NotNull(message = "Start time is required")
    @Future(message = "Event start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "Event end time must be in the future")
    private LocalDateTime endTime;

    private String bannerUrl;
    private String ageRestriction;

    @Builder.Default
    private EventStatus status = EventStatus.DRAFT;
}
