package pl.matros.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputHandlerTest {
    @Mock
    private Scanner scanner;

    @InjectMocks
    private InputHandler inputHandler;

    @Test
    void readIntInRange_ValidInput() {
        // Given
        when(scanner.nextLine()).thenReturn("5");

        // When
        int result = inputHandler.readIntInRange(1, 10);

        // Then
        assertEquals(5, result);
    }

    @Test
    void readIntInRange_InvalidInputThenValid() {
        // Given
        when(scanner.nextLine())
                .thenReturn("invalid")
                .thenReturn("11")
                .thenReturn("5");

        // When
        int result = inputHandler.readIntInRange(1, 10);

        // Then
        assertEquals(5, result);
    }
}