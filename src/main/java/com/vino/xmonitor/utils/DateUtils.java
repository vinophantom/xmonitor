package com.vino.xmonitor.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateUtils {
    


    public static LocalDateTime convertDateToLDT(Date date) {

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    
    }
   


   
    public static Date convertLDTToDate(LocalDateTime time) {
   
    return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
   
    }




    public static String formatTime(LocalDateTime time,String pattern) {

        return time.format(DateTimeFormatter.ofPattern(pattern));
       
    }

    public static String formatDate(Date date, String pattern) {
        return formatTime(convertDateToLDT(date), pattern);
    }
       
   
}