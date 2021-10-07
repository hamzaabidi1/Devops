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
public class EmployeTest {
	private static final Logger LOG = LogManager.getLogger(EmployeTest.class);
	@Autowired
	IEmployeService iempServ;
	@Autowired
	IEntrepriseService ientSer;
	@Autowired

	EmployeRepository empRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	private Employe employe;
	private Departement departement;
	private Contrat contrat;
	

	public void setUp() throws Exception {
		this.employe = new Employe();
		this.employe.setId(1);
		this.employe.setPrenom("hamza");
		this.employe.setNom("abidi");
		this.employe.setEmail("hamza.abidi1@esprit.tn");
		this.employe.setActif(true);
		this.employe.setRole(Role.INGENIEUR);
		this.departement = new Departement();
		this.departement.setName("new tech");
		this.contrat = new Contrat();
		this.contrat.setReference(1);
		this.contrat.setEmploye(this.employe);

	}
	
	@Test
	public void tests() {
		ajouterContrat();
		affecterEmployeADepartement();
		affecterContratAEmploye();
        deleteContratByRef();
   //  	deleteEmployeById();
	}

	public void affecterEmployeADepartement() {
		LOG.info("Start Method affecterEmployeADepartement ");
		this.departement.setId(ientSer.ajouterDepartement(this.departement));
		Assert.assertTrue(this.departement.getId() > 0);
		LOG.info(this.employe);
		LOG.info(this.departement.getId());
		iempServ.affecterEmployeADepartement(this.employe.getId(), this.departement.getId());
		this.employe = iempServ.getAllEmployes().stream().filter(x -> x.getId() == this.employe.getId()).findFirst()
				.get();
		Departement dd = null;
		// if (this.employe.getDepartements()==null)
		// {
		for (Departement d : this.employe.getDepartements()) {
			if (d.getId() == this.departement.getId())
				dd = d;
		}
		Assert.assertTrue(dd.getId() == this.departement.getId());
		// }
		LOG.info(this.employe);
		LOG.info("End Method affecterEmployeADepartement ");

	}

	public void ajouterContrat() {
		LOG.info("Start Method ajouterContrat");
		LOG.info(this.contrat);
		this.contrat.setEmploye(this.employe);

		this.contrat.setReference(iempServ.ajouterContrat(this.contrat));
		Assert.assertTrue(contrat.getReference() > 0);
		LOG.info("End Method ajouterContrat");

	}

	public void affecterContratAEmploye() {

		LOG.info("Start Method affecterContratAEmploye");
		LOG.info(this.contrat);
		LOG.info(this.employe);

		iempServ.affecterContratAEmploye(this.contrat.getReference(), this.employe.getId());
		this.employe = iempServ.getAllEmployes().stream().filter(x -> x.getId() == this.employe.getId()).findFirst()
				.get();
		Assert.assertEquals(this.employe.getContrat().getReference(), this.contrat.getReference());

		LOG.info("End Method affecterContratAEmploye");
	}

	public void deleteEmployeById() {
		LOG.info("Start Method deleteEmployeById");
		this.employe = iempServ.getAllEmployes().stream().filter(x -> x.getId() == this.employe.getId()).findFirst()
				.get();

		LOG.info(this.employe);
		iempServ.deleteEmployeById(this.employe.getId());
		 Assert.assertNull(iempServ.getEmployePrenomById(this.employe.getId()));
	//	Assert.assertFalse(empRepoistory.findById(this.employe.getId()).isPresent());
		LOG.info("End deleteEmployeById");

	}

	public void deleteContratByRef() {
		LOG.info("Start Method deleteContratByRef");
		LOG.info(this.contrat);

		iempServ.deleteContratById(this.contrat.getReference());
		Optional<Contrat> cont = contratRepoistory.findById(this.contrat.getReference());
//		System.out.println(cont.get().getReference());
//		Assert.assertTrue(cont.isPresent());

		LOG.info("End deleteContratByRef");

	}

	



}
