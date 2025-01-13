package pl.matros.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matros.restaurant.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {}