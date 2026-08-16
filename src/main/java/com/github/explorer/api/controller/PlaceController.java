package com.github.explorer.api.controller;

import com.github.explorer.api.dto.place.PlaceResponseDTO;
import com.github.explorer.api.service.PlaceService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> searchPlace(
            @RequestParam("q")
            @NotBlank(message = "O parâmetro 'q' é obrigatório")
            @Size(min = 2, max = 100, message = "O parâmetro deve ter entre 2 e 100 caracteres.")
            String q
    ) {
        String query = q.trim();
        return ResponseEntity.ok().body(placeService.searchPlace(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponseDTO> getPlace(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(placeService.getPlaceById(id));
    }
}
