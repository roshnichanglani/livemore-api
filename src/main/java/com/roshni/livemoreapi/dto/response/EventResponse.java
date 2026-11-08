package com.roshni.livemoreapi.dto.response;
import com.roshni.livemoreapi.entity.Event;
import com.roshni.livemoreapi.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String thingsToKnow;
    private String venueName;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bannerUrl;
    private String ageRestriction;
    private EventStatus status;
    private Boolean isFeatured;
    private Integer viewCount;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Nested response for the Category relationship
    private CategoryResponse category;

    public static EventResponse fromEntity(Event event) {
        if(event == null) return null;

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .slug(event.getSlug())
                .description(event.getDescription())
                .thingsToKnow(event.getThingsToKnow())
                .venueName(event.getVenueName())
                .location(event.getLocation())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .bannerUrl(event.getBannerUrl())
                .ageRestriction(event.getAgeRestriction())
                .status(event.getStatus())
                .isFeatured(event.getIsFeatured())
                .viewCount(event.getViewCount())
                .publishedAt(event.getPublishedAt())
                .category(event.getCategory() != null ? CategoryResponse.fromEntity(event.getCategory()) : null)
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

}
