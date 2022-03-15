package com.morena.netMain.logic.utils;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConvertor implements Converter<LocalDateTime, String> {

    public final static String dateFormat = "dd.MM.yyyy HH:mm:ss";
    public final static String dateFormatSQL = "yyyy-MM-dd HH:mm:ss";
    public final static String dateFormatWithout = "dd.MM.yyyy HH:mm";
    public final static String DATE_ONLY = "dd.MM.yyyy";

    /**
     * Возвращает отформатированную дату-время
     * @param localDateTime
     * @return
     */
    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static DateTimeFormatter dateFormatSQLFormatter() {
        return DateTimeFormatter.ofPattern(dateFormatSQL);
    }

    public static DateTimeFormatter dateFormatFormatter() {
        return DateTimeFormatter.ofPattern(dateFormat);
    }
}
