package main.main50;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main36 {


    public static void main(String[] args) {
        LocalDate localDate = LocalDate.parse("1900-10-29", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));

        System.out.println(date);

        if(date.after(Date.from(ZonedDateTime.now().minusYears(14).toInstant()))){

        }

        if(date.before(Date.from(ZonedDateTime.now().minusYears(100).toInstant()))){
            System.out.println("123123");
        }

    }


}
