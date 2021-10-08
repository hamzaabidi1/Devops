package tn.esprit.spring.test;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceTest {
	@Autowired
	IEmployeService iempServ;
	@Autowired
	IEntrepriseService ientSer;

	
	private Departement departement;
	private Contrat contrat;

	Employe employe = new Employe("Abidi", "Hamza", "hamza.abidi1@esprit.tn", false, Role.INGENIEUR);
	
	@Test
	public void testajouterEmploye() {
		Assert.assertEquals(1, iempServ.ajouterEmploye(employe));
	}
	

}
