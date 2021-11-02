package tn.esprit.spring.test;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.services.*;
import tn.esprit.spring.controller.*;
import tn.esprit.spring.repository.*;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSheetTest {
	
	
   @Autowired
	TimesheetServiceImpl TimesheetServiceImpl;
   @Autowired
   TimesheetRepository TRep;

   
   

	//private TimesheetPK timesheetPK;
	Mission m = new Mission("mariem","romdhani");
	@Test
	public void testAjouterMission() {
		Assert.assertEquals(11, TimesheetServiceImpl.ajouterMission(m));
	}
	

    @Test
	public void testgetAllEmployeByMission() throws ParseException {

		assertEquals("mariem", TimesheetServiceImpl.getAllEmployeByMission(1));

	}
	@Test
	public void testfindAllMissionByEmployeJPQL() throws ParseException {

		assertEquals("mariem", TimesheetServiceImpl.getAllEmployeByMission(1));
	}
	
	

	
	
    
	
	}



	
