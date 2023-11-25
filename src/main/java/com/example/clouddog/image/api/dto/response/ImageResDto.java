package com.example.clouddog.image.api.dto.response;

import com.example.clouddog.image.domain.Image;
import lombok.Builder;

@Builder
public record ImageResDto(
        Long imageId,
        String imageUrl
) {
    public static ImageResDto of(Image image) {
        return ImageResDto.builder()
                .imageId(image.getId())
                .imageUrl(image.getImageUrl())
                .build();
    }
}
