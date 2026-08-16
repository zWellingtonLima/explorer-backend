package com.github.explorer.api.repository;

import com.github.explorer.api.TestcontainersConfiguration;
import com.github.explorer.api.entity.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // Sobe apenas JPA, Hibernate e repositorios - sem controller ou service
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Impede spring de tentar substituir Postgres por uma base embutida
@Import(TestcontainersConfiguration.class)
class PlaceRepositoryIntegrationTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    void searchPlaceByNameOrCity_whenQueryMatchesCity_returnsPlacesAlphabetically() {
        List<String> placeNames = placeRepository.searchPlaceByNameOrCity("Porto")
                .stream()
                .map(Place::getName)
                .toList();

        assertEquals(
                List.of("Livraria Lello", "Ribeira do Porto"),
                placeNames
        );
    }
}
