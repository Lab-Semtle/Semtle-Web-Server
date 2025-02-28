package com.archisemtle.semtlewebserverspring.common.utils;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConvertUtils {


    public static String changeStringToHTML(String data){
        if (data != null) {
            data = "<" + data + ">";
        }
        return data;
    }

    public static String changeDateToString(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }

    public static String changeFullDateToString(Date date){
        // "yyyy-MM-dd'T'HH:mm:ss'Z'" 형식으로 출력 (UTC 기준)
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneOffset.UTC);
        return formatter.format(date.toInstant());
    }

    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("날짜 파싱 에러: " + dateStr);
        }
    }

    public static String getNumber(String strParam) {
        return strParam.replaceAll("\\D+", "");
    }

    public static String changeHTMLToString (String htmlMsg){
        if (htmlMsg != null && htmlMsg.startsWith("<") && htmlMsg.endsWith(">")) {
            return htmlMsg.substring(1, htmlMsg.length() - 1);
        }else{
            return "-";
        }
    }
}
