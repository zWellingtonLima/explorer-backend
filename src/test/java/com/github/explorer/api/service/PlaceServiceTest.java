package com.github.explorer.api.service;

import com.github.explorer.api.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PlaceServiceTest {

    @Test
    void getPlaceById_whenPlaceDoesNotExist_throwsEntityNotFoundException() {
        PlaceRepository placeRepository = mock(PlaceRepository.class);
        PlaceService placeService = new PlaceService(placeRepository);
        Long placeId = 1L;

        when(placeRepository.findById(placeId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> placeService.getPlaceById(placeId));
        verify(placeRepository).findById(placeId);
    }

    @Test
    void searchPlace_whenRepositoryFindsNoPlaces_returnsEmptyList() {
        PlaceRepository placeRepository = mock(PlaceRepository.class);
        PlaceService placeService = new PlaceService(placeRepository);
        String placeSearch = "Porto";

        when(placeRepository.searchPlaceByNameOrCity(placeSearch)).thenReturn(List.of());
        assertTrue(placeService.searchPlace(placeSearch).isEmpty());
        verify(placeRepository).searchPlaceByNameOrCity(placeSearch);
    }
}
