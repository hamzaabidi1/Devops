package tn.esprit.spring.test;

import org.junit.Assert;
import org.junit.Test;
import tn.esprit.spring.controller.IControllerEntrepriseImpl;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.EntrepriseServiceImpl;

public class IControllerEntrepriseImplTest {
	IControllerEntrepriseImpl IControllerEntrepriseImpl = new IControllerEntrepriseImpl() ;
	//@Autowired
	EntrepriseServiceImpl EntrepriseServiceImpl = new EntrepriseServiceImpl();
	Entreprise e = new Entreprise();
	
	@Test
	public void testAjouterEntreprise() {
		e.setId(2);
		e.setName("test");
		e.setRaisonSocial("test");
		Assert.assertNotNull("Name mustn't be null", e.getName());
		Assert.assertNotNull("Raison Social mustn't be null", e.getRaisonSocial());
		EntrepriseServiceImpl.ajouterEntreprise(e);
		
	}
	
	@Test
	public void testDeleteEntrepriseById()
	{
		int id = 0;
		int id_entreprise = e.getId();
		Assert.assertEquals(id_entreprise, id);
		EntrepriseServiceImpl.deleteEntrepriseById(id);
		
	}
	
	@Test
	public void testGetEntrepriseById()
	{
		int id = 0;
		int id_entreprise = e.getId();
		Assert.assertEquals(id_entreprise, id);
		EntrepriseServiceImpl.getEntrepriseById(id);
		
	}

}
