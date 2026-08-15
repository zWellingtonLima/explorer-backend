package com.github.explorer.api.service;

import com.github.explorer.api.dto.place.PlaceResponseDTO;
import com.github.explorer.api.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaceResponseDTO> searchPlace(String query) {
        return placeRepository
                .searchPlaceByNameOrCity(query)
                .stream()
                .map(PlaceResponseDTO::from).toList();
    }

}
