package org.company.user.infrastructure.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilDateTest {

    @Test
    void validateWhenInstanceDateIsNotCorrect() {
        assertThrows(IllegalStateException.class, UtilDate::new);
    }

    @Test
    void validateDateWhenDateIsCorrect() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        assertDoesNotThrow(() -> LocalDateTime.parse(UtilDate.getCurrentDate(), formatter));
    }

    @Test
    void validateDateWhenDateIsNotCorrect() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:ss");

        assertThrows(DateTimeParseException.class, () -> LocalDateTime.parse(UtilDate.getCurrentDate(), formatter));
    }

}