package tn.esprit.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceTest {
	@Autowired
	IEmployeService iempServ;
	@Autowired
	IEntrepriseService ientSer;
	private Contrat contrat;
	private Employe employe;
	private Entreprise entreprise;
	private Mission mission;

	@Before
	  public void initialiser() throws Exception {
		contrat = new Contrat(null, null, 2000);
		employe = new Employe("Abidi", "Hamza", "hamza.abidi1@esprit.tn", false, Role.INGENIEUR);
		entreprise = new Entreprise("tek", null);
		mission = new Mission(null, null);
		
	  }

	  @After
	  public void nettoyer() throws Exception {
		  contrat = null;
		  employe = null;
		  entreprise = null;
		  mission=null;
	  }
	
	
	@Test
	public void testajouterEmploye() {
		Assert.assertEquals(1, iempServ.ajouterEmploye(employe));
	}
	@Test
	public void testajouterContrat() {
		Assert.assertEquals(1, iempServ.ajouterContrat(contrat));
	}
	@Test
	public void testgetEmployePrenomById() {
		Assert.assertEquals("Hamza",iempServ.getEmployePrenomById(1));

	}
	@Test
	public void testgetNombreEmployeJPQL() {
		Assert.assertEquals(4, iempServ.getNombreEmployeJPQL());
		
	}
	@Test
	public void testgetAllEmployeNamesJPQL() {
		List<String> lst =(List<String>) iempServ.getAllEmployeNamesJPQL();
		assertThat(lst).size().isGreaterThan(0);
	}
	@Test
	public void testgetAllEmployeByEntreprise() {
		entreprise.setId(1);
		List<Employe> lst =(List<Employe>) iempServ.getAllEmployeByEntreprise(entreprise);
		assertThat(lst).size().isGreaterThan(0);
	}
	@Test
	public void testgetSalaireByEmployeIdJPQL() {
		Assert.assertEquals(2000, iempServ.getSalaireByEmployeIdJPQL(1),0);
		
	}
	@Test
	public void testgetSalaireMoyenByDepartementId() {
		Assert.assertEquals(2000, iempServ.getSalaireMoyenByDepartementId(1),0);
	}
	@Test
	public void testgetTimesheetsByMissionAndDate() throws ParseException {
		mission.setId(1);
		employe.setId(1);
		List<Timesheet> lst =(List<Timesheet>) iempServ.getTimesheetsByMissionAndDate(employe, mission,new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-01"), new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-02"));
		assertThat(lst).size().isGreaterThan(0);
	}
	
	@Test
	public void testgetAllEmployes() {
		List<Employe> lst =(List<Employe>) iempServ.getAllEmployes();
		assertThat(lst).size().isGreaterThan(0);
	}	
}
