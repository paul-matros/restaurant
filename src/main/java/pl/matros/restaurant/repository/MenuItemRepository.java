package pl.matros.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matros.restaurant.model.Cuisine;
import pl.matros.restaurant.model.MenuItem;
import pl.matros.restaurant.model.MenuItemType;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByCuisineAndType(Cuisine cuisine, MenuItemType menuItemType);

    List<MenuItem> findByType(MenuItemType menuItemType);
}