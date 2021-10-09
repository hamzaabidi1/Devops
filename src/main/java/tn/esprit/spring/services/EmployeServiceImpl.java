package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	 private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		LOGGER.info("In mettreAjourEmailByEmployeId ");
		LOGGER.debug("start search");
		Employe employe = employeRepository.findById(employeId).get();
		LOGGER.debug("update employe email");
		employe.setEmail(email);
		LOGGER.debug("save employe");
		employeRepository.save(employe);
		LOGGER.info("end search ");

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		LOGGER.info("IN affecterEmployeADepartement ");
		LOGGER.debug("start search department");
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		LOGGER.debug("start search employe");
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		LOGGER.info("if department is empty");
		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			LOGGER.debug("add employe to list");
			employes.add(employeManagedEntity);
			LOGGER.debug("add list employe to empty department");
			depManagedEntity.setEmployes(employes);
			LOGGER.info("add employe to empty department seccess ");
		}else{
			LOGGER.debug("add list employe to department");
			depManagedEntity.getEmployes().add(employeManagedEntity);
			LOGGER.info("add employe to department seccess ");
		}

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		LOGGER.info("IN desaffecterEmployeDuDepartement ");
		LOGGER.debug("start search department");
		Departement dep = deptRepoistory.findById(depId).get();
		LOGGER.debug("get number of employees");
		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			LOGGER.info("test exist employe");
			if(dep.getEmployes().get(index).getId() == employeId){
				LOGGER.debug("remove employe");
				dep.getEmployes().remove(index);
				LOGGER.info("delete employe success");
				break;//a revoir
				
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		LOGGER.info("IN affecterContratAEmploye ");
		LOGGER.debug("start search contrat");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		LOGGER.debug("start search employe");
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		LOGGER.debug("add contrat to employe");
		contratManagedEntity.setEmploye(employeManagedEntity);
		LOGGER.debug("save changes");
		contratRepoistory.save(contratManagedEntity);
		LOGGER.info("affected with success ");
		
	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}
	public void deleteEmployeById(int employeId)
	{
		LOGGER.info("IN deleteEmployeById ");
		LOGGER.debug("start search employe");
		Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			LOGGER.debug("employe removed from department");
			dep.getEmployes().remove(employe);
		}
		LOGGER.debug("employe removed");
		employeRepository.delete(employe);
		LOGGER.info("end delete employe");
	}

	public void deleteContratById(int contratId) {
		LOGGER.info("IN deleteContratById ");
		LOGGER.debug("start search contrat");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		LOGGER.debug("delete contrat");
		contratRepoistory.delete(contratManagedEntity);
		LOGGER.info("delete conrtat with success  ");
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		LOGGER.info("IN mettreAjourEmailByEmployeIdJPQL ");
		LOGGER.debug("update email employe");
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
		LOGGER.info("update email to employe success ");

	}
	public void deleteAllContratJPQL() {
		LOGGER.info("IN deleteAllContratJPQL ");
		LOGGER.debug("delete all contrat");
         employeRepository.deleteAllContratJPQL();
         LOGGER.info("delete All Contrat with success");
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
