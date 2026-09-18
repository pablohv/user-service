package org.company.user.infrastructure.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class UtilDate {

    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final Clock CLOCK_ZONE_MEXICO = Clock.system(ZoneId.of("America/Mexico_City"));

    public static String getCurrentDate() {
        return LocalDateTime.now(CLOCK_ZONE_MEXICO).format(DATE_TIME);
    }

}
