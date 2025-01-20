package pl.matros.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.matros.restaurant.model.MenuDisplayable;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuSelector {
    private final InputHandler inputHandler;

    public <T extends MenuDisplayable> T selectMenuItem(List<T> items, String menuTitle) {
        printMenu(items, menuTitle);
        int selected = inputHandler.readIntInRange(0, items.size());
        return selected == 0 ? null : items.get(selected - 1);
    }

    private <T extends MenuDisplayable> void printMenu(List<T> items, String menuTitle) {
        System.out.println(menuTitle + "(or 0 to skip):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getDisplayName());
        }
    }
}