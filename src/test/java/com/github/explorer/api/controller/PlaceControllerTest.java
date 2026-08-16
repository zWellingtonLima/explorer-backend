package com.github.explorer.api.controller;

import com.github.explorer.api.dto.place.PlaceResponseDTO;
import com.github.explorer.api.service.PlaceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc; // Recebe uma chamada HTTP simulada sem chamar o controller diretamente

    @MockitoBean // Coloca o mock no contexto Spring para o controller receber essa dep no construtor
    private PlaceService placeService;

    @Test
    void getPlace_whenPlaceExists_returnsOkAndPlaceResponse() throws Exception {
        PlaceResponseDTO response = new PlaceResponseDTO(
                1L,
                "Torre de Belém",
                "Lisboa",
                "PT",
                38.6916,
                -9.2160
        );

        when(placeService.getPlaceById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/places/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Torre de Belém"))
                .andExpect(jsonPath("$.city").value("Lisboa"))
                .andExpect(jsonPath("$.countryCode").value("PT"));

        verify(placeService).getPlaceById(1L);
    }

    @Test
    void getPlace_whenPlaceDoesNotExist_returnsNotFound() throws Exception {
        when(placeService.getPlaceById(999L)).thenThrow(new EntityNotFoundException("Local não encontrado"));

        mockMvc.perform(get("/api/v1/places/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Não encontrado"))
                .andExpect(jsonPath("$.detail").value("Local não encontrado"))
                .andExpect(jsonPath("$.type").value("urn:explorer:problem:notfound"));

        verify(placeService).getPlaceById(999L);
    }
}
