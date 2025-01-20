package pl.matros.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matros.restaurant.model.MenuItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuSelectorTest {
    @Mock
    private InputHandler inputHandler;

    @InjectMocks
    private MenuSelector menuSelector;

    @Test
    void selectMenuItem_ValidSelection() {
        // Given
        MenuItem item = new MenuItem();
        item.setName("Test Item");
        List<MenuItem> items = List.of(item);
        when(inputHandler.readIntInRange(0, 1)).thenReturn(1);

        // When
        MenuItem result = menuSelector.selectMenuItem(items, "Select item");

        // Then
        assertEquals(item, result);
    }

    @Test
    void selectMenuItem_SkipSelection() {
        // Given
        List<MenuItem> items = List.of(new MenuItem());
        when(inputHandler.readIntInRange(0, 1)).thenReturn(0);

        // When
        MenuItem result = menuSelector.selectMenuItem(items, "Select item");

        // Then
        assertNull(result);
    }
}