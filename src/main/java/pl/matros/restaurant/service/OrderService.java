package pl.matros.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matros.restaurant.model.Cuisine;
import pl.matros.restaurant.model.MenuItem;
import pl.matros.restaurant.model.MenuItemType;
import pl.matros.restaurant.repository.CuisineRepository;
import pl.matros.restaurant.repository.MenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CuisineRepository cuisineRepository;
    private final MenuItemRepository menuItemRepository;

    public List<Cuisine> getAllCuisines() {
        return cuisineRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByCuisineAndType(Cuisine cuisine, MenuItemType menuItemType) {
        return menuItemRepository.findByCuisineAndType(cuisine, menuItemType);
    }

    public List<MenuItem> getMenuItemsByType(MenuItemType menuItemType) {
        return menuItemRepository.findByType(menuItemType);
    }


}
