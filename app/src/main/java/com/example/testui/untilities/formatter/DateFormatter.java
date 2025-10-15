package com.example.testui.untilities.formatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    public static String formatDate(String inputDate) {
        if (inputDate == null || inputDate.isEmpty()) return "";

        try {
            // Cố gắng parse dạng ISO 8601: "2025-10-12T17:09:15.000000Z"
            Instant instant = Instant.parse(inputDate);
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e1) {
            try {
                // Dạng có giờ phút giây: "2025-09-11 19:25:30"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(inputDate, formatter);
                return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e2) {
                try {
                    // Dạng chỉ có ngày: "1970-09-12"
                    LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e3) {
                    // Trả về nguyên chuỗi nếu không parse được
                    return inputDate;
                }
            }
        }
    }
}
