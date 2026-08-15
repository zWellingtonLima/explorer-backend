package com.github.explorer.api.repository;

import com.github.explorer.api.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("""
            SELECT p FROM Place p
            WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(p.city) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY p.name ASC
            """)
    List<Place> searchPlaceByNameOrCity(@Param("query") String query);

}
