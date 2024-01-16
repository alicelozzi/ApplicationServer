/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.banca.java_base_integrazioni.data_ora;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import static java.time.DayOfWeek.WEDNESDAY;
import java.time.LocalTime;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 *
 * @author Jpr2
 */
public class LocalDateTimeSample {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    public static void main(String[] args) {
        
//        ZoneId zoneLondon;// = new ZoneId();
//        
//        LocalDateTime dateTimeLondon = LocalDateTime.now(zoneLondon);
        
        
        /*****************************************/
        /*** LocalDate: GESTIONE CON SOLO DATA ***/
        /*****************************************/
        LocalDate date = LocalDate.of(2014, Month.SEPTEMBER, 9);
        int year = date.getYear();                                              //2014
        Month month = date.getMonth();                                          //SEPTEMBER
        int dom = date.getDayOfMonth();                                         //9
        DayOfWeek dow = date.getDayOfWeek();                                    //Tuesday
        int len = date.lengthOfMonth();                                         //30 (Numero di gg di settembre)
        boolean leap = date.isLeapYear();                                       //false (2014 non è bisestile)
        
        LocalDate date1 = LocalDate.of(2014, Month.SEPTEMBER, 10);              //date1=> 10-09-2014
        LocalDate date2 = date1.withYear(2015);                                 //date2=> 10-09-2015 (varia l'anno, da 2014 a 2015)
        date2 = date2.plusMonths(2);                                            //date2=> 10-10-2015 (aggiunge un mese alla data)
        date2 = date2.minusDays(1);                                             //date2=> 09-10-2015 (sottrae un giorno alla data)
        
        
        LocalDate date3 = LocalDate.of(2014, Month.SEPTEMBER, 10);              //date3=> 10-09-2014
        date3 = date3.with(lastDayOfMonth());                                   //date3=> 30-09-2014 (ultimo giorno del mese)
        date3 = date3.with(nextOrSame(WEDNESDAY));                              //date3=> 01-10-2014 (prossimo mercoledì è)
        
        /****************************************/
        /*** LocalDate: GESTIONE CON SOLO ORA ***/
        /****************************************/
        LocalTime time = LocalTime.of(20, 30);                                  //imposta roario alle 20:30:00
        int hour = time.getHour();                                              //ora=> 20
        int minute = time.getMinute();                                          //minuto=> 30
        time = time.withSecond(6);                                              // 20:30:06 (incrementa di 6 secondi)
        time = time.plusMinutes(3);                                             // 20:33:06 (incrementa di 3 minuti)
        
        /***********************************************/
        /*** LocalDateTime: GESTIONE CON DATA ED ORA ***/
        /***********************************************/
        LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 9, 19, 46, 45);    //data ed ora finale: D->09-09-2014 T->19:46:45.000000000 
        LocalDateTime fromDateTime = LocalDateTime.of(1984, 12, 16, 7, 45, 55); //data ed ora iniziale: D->16-12-2014 T->07:45:55.000000000 

        Period period = getPeriod(fromDateTime, toDateTime);                    //Period: intervallo espresso in forma di giorno, mese, anno
        long time1[] = getTime(fromDateTime, toDateTime);                       //Time: intervallo espresso in forma di secondi e nanosecondi

        System.out.println(
                period.getYears() + " years " + 
                period.getMonths() + " months " + 
                period.getDays() + " days " +
                time1[0] + " hours " +
                time1[1] + " minutes " +
                time1[2] + " seconds."
        );


    }

    private static Period getPeriod(LocalDateTime from, LocalDateTime to) {
        return Period.between(from.toLocalDate(), to.toLocalDate());
    }

    private static long[] getTime(LocalDateTime from, LocalDateTime to) {
        LocalDateTime today = LocalDateTime.of(to.getYear(),
                to.getMonthValue(), to.getDayOfMonth(), from.getHour(), from.getMinute(), from.getSecond());
        Duration duration = Duration.between(today, to);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }

}


