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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOTest {

    private static ParkingSpotDAO parkingSpotDAO ;

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }
    @BeforeEach
    private void setUpPerTest() throws Exception {
        dataBasePrepareService.clearDataBaseEntries();
    }
    @Test
    public void getNextAvailableParkingSpotTest(){
        //Init test param
        int result = 0 ;
        //Call service and store result
        result = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        //Assert result
        assertTrue(result >= 1);
    }

    @Test
    public void updateParkingSpotTest(){
        boolean result ;

        ParkingSpot parkingSpot = new ParkingSpot(1 , ParkingType.CAR , false);

        Ticket ticket = new Ticket();
        ticket.setParkingSpot(parkingSpot);

        parkingSpot = ticket.getParkingSpot();
        parkingSpot.setAvailable(true);
        result = parkingSpotDAO.updateParking(parkingSpot);

        assertTrue(result == true);

    }

}
