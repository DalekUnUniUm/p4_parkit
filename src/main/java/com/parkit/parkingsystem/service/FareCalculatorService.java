package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }


        long inHour = ticket.getInTime().getTime();
        System.out.println("inHour = " + inHour );
        long outHour = ticket.getOutTime().getTime();
        System.out.println("outHour = " + outHour );
        //TODO: Some tests are failing here. Need to check if this logic is correct

        long diff = outHour - inHour ;

        //int diffHours = (int) (diff / (60 * 60 * 1000));
        int diffMinutes = (int) (diff / (60 * 1000));
        System.out.println(diffMinutes);
        //int diffSeconds = (int) (diff / 1000 % 60);
        //System.out.println("Hours = " + diffHours + " Minutes = " + diffMinutes + " Secondes = " + diffSeconds );

        double percent = (double) diffMinutes / 60;

        System.out.println(percent);

        if(diffMinutes <= 30){
            ticket.setPrice(0);
        }
        else if(diffMinutes > 30){
            switch (ticket.getParkingSpot().getParkingType()){
                case CAR: {
                    //System.out.println("Fare =  " + duration * Fare.CAR_RATE_PER_HOUR);
                    if(ticket.getDiscount()== true){
                        double discount = (percent * Fare.CAR_RATE_PER_HOUR) / 100 ;
                        discount = discount * 5 ;
                        System.out.println("DiscountOriginal = " + ((0.75 * Fare.CAR_RATE_PER_HOUR) - discount));
                        ticket.setPrice((percent * Fare.CAR_RATE_PER_HOUR) - discount);
                    }
                    else
                        ticket.setPrice(percent * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
                case BIKE: {
                    if(ticket.getDiscount()== true){
                        double discount = (percent * Fare.BIKE_RATE_PER_HOUR) / 100 ;
                        discount = discount * 5 ;
                        ticket.setPrice((percent * Fare.BIKE_RATE_PER_HOUR) - discount);
                    }
                    else
                        ticket.setPrice(percent * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
                default: throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
    }

}