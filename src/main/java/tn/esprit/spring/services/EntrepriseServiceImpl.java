package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	
	private static final Logger logger = LoggerFactory.getLogger(EntrepriseServiceImpl.class);
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	Entreprise ent =  new Entreprise();
	Departement dep = new Departement();
	
	public int ajouterEntreprise(Entreprise entreprise) {
		System.out.println("**********");
		
		try{
			logger.info("Ajouter Entreprise");
			logger.debug("je suis entrain d'ajouter une entreprise");
			entrepriseRepoistory.save(entreprise);
			logger.info("entreprise ajoutee");
		}catch (Exception e) {
			logger.error("Erreur : " );
		}finally{
			logger.info("Methode ajouter entreprise fini");
		}
	
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {

		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
				Optional <Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
				Optional <Departement> depManagedEntity = deptRepoistory.findById(depId);
				if (entrepriseManagedEntity.isPresent()) {
					ent = entrepriseManagedEntity.get();
					if (depManagedEntity.isPresent()) {
						dep = depManagedEntity.get();
					}
				}
				dep.setEntreprise(ent);
				deptRepoistory.save(dep);
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		
		Optional <Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
		if(entrepriseManagedEntity.isPresent()) {
			ent = entrepriseManagedEntity.get();
		}
		List<String> depNames = new ArrayList<>();
		for(Departement dep : ent.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
			
			try{
				
				logger.info("suppression d'une entreprise : ");
				logger.debug("selection d'une entreprise a supprimé : ");
				Optional <Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
				if (entreprise.isPresent()) {
					ent = entreprise.get();
				}
				entrepriseRepoistory.delete(ent);
				logger.debug("je viens de supprimer entreprise: ");
				logger.info("suppression sans erreurs " );
			}catch(Exception e){
				logger.error("Erreur dans la suppression de l'entreprise: "+e);
			}finally{
				logger.info("Methode supprimer entreprise fini");
			}
			
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		
		Optional <Departement> depEnt = deptRepoistory.findById(depId);
		if (depEnt.isPresent()) {
			dep = depEnt.get();
		}
		deptRepoistory.delete(dep);	
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		
		try{
			logger.info("affichage d'une entreprise par id : ");
			logger.debug("entrain d'afficher entreprise : ");
			Optional <Entreprise> x  = entrepriseRepoistory.findById(entrepriseId);
			if (x.isPresent()) {
				ent = x.get();
			}
			logger.debug("je viens d'afficher entreprise: ");
			logger.info("affichage sans erreurs " );
		}
		catch(Exception e){
			logger.error("Erreur dans l'affichage de l'entreprise: "+e);
		}finally{
			logger.info("Methode affichage entreprise fini");
		}
		return ent;	
	}

}
