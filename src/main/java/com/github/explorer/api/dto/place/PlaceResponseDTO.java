package com.github.explorer.api.dto.place;

import com.github.explorer.api.entity.Place;

public record PlaceResponseDTO(
        Long id,
        String name,
        String city,
        String countryCode,
        Double latitude,
        Double longitude
) {
    public static PlaceResponseDTO from(Place place) {
        return new PlaceResponseDTO(
                place.getId(),
                place.getName(),
                place.getCity(),
                place.getCountryCode(),
                place.getLatitude(),
                place.getLongitude()
        );
    }
}
