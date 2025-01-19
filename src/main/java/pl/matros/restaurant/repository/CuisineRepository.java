package pl.matros.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matros.restaurant.model.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}
