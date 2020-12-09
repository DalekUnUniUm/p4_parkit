package com.parkit.parkingsystem.integration.DAO;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketDAOTest {

    private static TicketDAO ticketDAO ;
    private static Ticket ticket ;
    private static ParkingSpot parkingSpot ;
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception {
        ticketDAO = new TicketDAO();
        ticket = new Ticket();
        parkingSpot = new ParkingSpot(1, ParkingType.CAR , false);
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }
    @BeforeEach
    private void setUpPerTest() throws Exception {
        dataBasePrepareService.clearDataBaseEntries();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
    }

    @Test
    public void saveTicketTest(){
        boolean ret = false ;
        ret = ticketDAO.saveTicket(ticket);
        assertTrue(ret == false);
    }
    @Test
    public void getTicketTest(){
        saveTicketTest();
        ticketDAO.getTicket("ABCDEF");
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
    }
    @Test
    public void updateTicketTest(){
        boolean ret = false ;
        saveTicketTest();
        ticket.setOutTime(new Date(System.currentTimeMillis()));
        ret = ticketDAO.updateTicket(ticket);
        //System.out.println("UpdateTicket = " + ticketDAO.updateTicket(ticket));
        assertTrue(ret == true);
    }
    @Test
    public void getIfRegNumberExistTest(){
        boolean ret = false ;
        saveTicketTest();
        ret = ticketDAO.getIfRegNumberExist("ABCDEF");
        assertTrue(ret == true);
    }


}
