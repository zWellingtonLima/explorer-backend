package com.github.explorer.api.service;

import com.github.explorer.api.dto.place.PlaceResponseDTO;
import com.github.explorer.api.entity.Place;
import com.github.explorer.api.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    // ====================================================
    // GET
    // ====================================================
    @Transactional(readOnly = true)
    public List<PlaceResponseDTO> searchPlace(String query) {
        return placeRepository
                .searchPlaceByNameOrCity(query)
                .stream()
                .map(PlaceResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public PlaceResponseDTO getPlaceById(Long placeId) {
        Place p = placeRepository.findById(placeId).orElseThrow(() -> new EntityNotFoundException("Local não encontrado"));
        return PlaceResponseDTO.from(p);
    }
}
