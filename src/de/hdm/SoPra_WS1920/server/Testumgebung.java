package de.hdm.SoPra_WS1920.server;
import de.hdm.SoPra_WS1920.shared.bo.Person;

public class Testumgebung {

	public static void main(String[] args) {
		
		/*
		 * Mit dieser Klasse werden s�mtliche Methoden aus der Impl getestet um die volle Funktionalit�t
		 * der Methoden zu pr�fen. Sobald eine Methode von allen Usern erfolgreich getestet wurde,
		 * bitte in der GitHub Issue Liste abhaken.
		 */
		
		CinemaAdministrationImpl implAdmin = new CinemaAdministrationImpl();
		implAdmin.init();
		
		SurveyManagementImpl implSurveyManagement = new SurveyManagementImpl();
		implSurveyManagement.init();
		
		//implSurveyManagement.createPerson("Yesin","Soufi", "ys018@hdm-stuttgart.de");
		
		implAdmin.createPerson("Yesin", "Soufi", "ys018@hdm-stuttgart.de");
		
		/*
		 * Erstellen einer Person
		 * implSurveyManagement.createPerson(1, "Yesin", "Soufi", "ys018@hdm-stuttgart.de", true, "2019-11-22 16:47,08.128");
		 */
		
		
		/*
		 	Update eines Kinos
		 	Cinema c = implAdmin.getCinemaById(1);
			System.out.println(c.getName());
			c.setName("Metropol");
			c.setPostCode(0711);
			c.setStreetNo("34");
			impl.updateCinema(c);
			System.out.println(c.getName());
			System.out.println(c.getPostCode());
			System.out.println(c.getStreetNo());	
			*/
		
		/*	
		 	L�schen einer Person
		 	Person p = new Person();
			p.setPersonId(1);
			implSurveyManagement.deletePerson(p);
		 */
		 
		
		

	}

}
