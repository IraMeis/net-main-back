package com.morena.netMain.logic.utils;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConvertor implements Converter<LocalDate, String> {

    public final static String dateFormat = "dd.MM.yyyy";

    /**
     * Возвращает отформатированное время
     * @param localDate
     * @return
     */
    @Override
    public String convert(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(dateFormat));
    }
}
