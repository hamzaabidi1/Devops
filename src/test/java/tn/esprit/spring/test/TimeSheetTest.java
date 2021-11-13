package tn.esprit.spring.test;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.services.*;
import tn.esprit.spring.controller.*;
import tn.esprit.spring.repository.*;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.After;
import org.junit.Assert;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSheetTest {
	
	
   @Autowired
	TimesheetServiceImpl timesheetServiceImpl;
   @Autowired
   MissionRepository missionRepository;
   
     private Mission mission;
     private int idm;
     private Employe employe;
     private int ide;
     List<Employe> l;
     List<Mission> m;
     
     @Before
     public void init() {
      mission = new Mission("mariem","romdhani");
      idm=timesheetServiceImpl.ajouterMission(mission);
       l =timesheetServiceImpl.getAllEmployeByMission(idm);
       m=  timesheetServiceImpl.findAllMissionByEmployeJPQL(ide);
     }
    
     @After
     public void restart() {
    	
    	 missionRepository.delete(mission);
     }



	@Test
	public void testAjouterMission() {
		Assert.assertEquals(idm, timesheetServiceImpl.ajouterMission(mission));
	}
	

    @Test
	public void testgetAllEmployeByMission() throws ParseException {

		assertEquals(l , timesheetServiceImpl.getAllEmployeByMission(idm));

	}
	@Test
	public void testfindAllMissionByEmployeJPQL() throws ParseException {

		assertEquals(m , timesheetServiceImpl.findAllMissionByEmployeJPQL(ide));
	}
	
	

	
	

	
	}



	
