package de.hdm.SoPra_WS1920.server.db;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.SoPra_WS1920.shared.bo.Survey;

/**
 *
 * Mapper-Klasse, die <code>Survey</code>-Objekte auf relationale Datenbank abbildet.
 * Anhand von den Methoden kÃ¯Â¿Â½nnen Objekte gesucht, erzeugt, bearbeitet und gelÃ¯Â¿Â½scht werden.
 * Objekte kÃ¯Â¿Â½nnen in DB-Strukturen umgewandelt werden und DB-Strukturen in Objekte.
 * 
 * @author shila
 */ 
public class SurveyMapper {
	
/**
 * Die Klasse SurveyMapper wird nur einmal instanziiert (Singleton-Eigenschaft).
 * Die folgende Variable ist durch den Bezeichner <code>static</code> nur einmal fÃ¯Â¿Â½r 
 * alle Instanzen der Klasse vorhanden. Die einzige Instanz dieser Klasse wird darin gespeichert.
 */
	
	private static SurveyMapper surveyMapper = null;
	
/**
 * GeschÃ¯Â¿Â½tzter Konstruktor, der verhindert, dass mit dem new-Operator
 * neue Instanzen der Klasse erstellt werden.
 */
	
	protected SurveyMapper() {
		
	}
	
/**
 * Folgende statische Methode sichert die Singleton-Eigenschaft.
 * Es wird dafÃ¯Â¿Â½r gesorgt, dass nur eine einzige Instanz von
 * <code>SurveyMapper</code> existiert.
 * SurveyMapper wird durch den Aufruf dieser statischen Methode instanziiert, 
 * nicht durch den new-Operator.
 * Aufruf der Methode durch: <code>SurveyMapper.surveyMapper()</code>
 * 
 * @return <code>SurveyMapper</code>-Objekt
 */
 
	public static SurveyMapper surveyMapper() {
		if(surveyMapper == null) {
			surveyMapper = new SurveyMapper();
		}
		
		return surveyMapper;
	}
	
	/**
     * @param id (PrimÃ¯Â¿Â½rschlÃ¯Â¿Â½ssel-Attribut)
     * @return Survey-Objekt, das dem Ã¯Â¿Â½bergebenen SchlÃ¯Â¿Â½ssel entspricht, null
     * bei nicht vorhandenem DB-Tupel.
     */
	
	public Survey findSurveyByID(int id) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM survey WHERE id= " + id);
			
			if(rs.next()) {
				
				Survey s = new Survey();
				s.setId(rs.getInt("id"));
				s.setStartDate(rs.getDate("startDate"));
				s.setEndDate(rs.getDate("endDate"));
				s.setGroupFK(rs.getInt("groupFK"));
				
				return s;
			}
			
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	/**
     * EinfÃ¯Â¿Â½gen eines <code>Survey</code>-Objekts in die DB.
     * PrÃ¯Â¿Â½fung und ggf. Korrektur des PrimÃ¯Â¿Â½rschlÃ¯Â¿Â½ssels
     * @param survey das zu speichernde Objekt
     * @return das Ã¯Â¿Â½bergebene Objekt, mit ggf. korrigierter <code>id</code>.
     */
	
	public Survey insertSurvey(Survey s) {
		Connection con = DBConnection.connection();
		
		try {
		
			Statement stmt = con.createStatement();
			
				stmt.executeUpdate("INSERT INTO survey(id, groupFK, startDate, endDate)"
					+ "VALUES ('"
					+ s.getId()	
					+ "','"
					+ s.getGroupFK()
					+ "','"
					+ s.getStartDate()
					+ "','"
					+ s.getEndDate()+ "')");
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		
		}
		
		return s;
	}
	
	/**
     * Ein Objekt wird wiederholt in die DB geschrieben.
     * 
     * @param s, das Objekt, das in die DB geschrieben werden soll
     * @return das Objekt, das als Parameter Ã¯Â¿Â½bergeben wird -> s
     */
    public Survey updateSurvey(Survey s) {
        Connection con = DBConnection.connection();
        
        try {
        	con.setAutoCommit(false);
        	Statement stmt = con.createStatement();
        	
        	stmt.executeUpdate("UPDATE survey SET startDate='"+s.getStartDate()
        	+ "', endDate='"+s.getEndDate()
        	+ "', groupFK='"+s.getGroupFK()
        	+ "' WHERE id=" + s.getId());
        	con.setAutoCommit(true);
        }
        catch(SQLException e2) {
        e2.printStackTrace();
       
    }
        return s;
    }
    
    /**
     * LÃ¯Â¿Â½schen von Daten eines <code>Survey</code>-Objekts aus der Datenbank
     * @param s, das zu lÃ¯Â¿Â½schende Objekt 
     */
    public void deleteSurvey(Survey s) {
    	Connection con = DBConnection.connection();
    	
    	try {
    		Statement stmt = con.createStatement();
    		
    		stmt.executeUpdate("DELETE FROM survey WHERE id= "+s.getId());
    		
    	}
    	catch(SQLException e2) {
    		e2.printStackTrace();
    	}
        
        
    }
    
    /**
     * Auslesen der Survey-Objekte mit gegebenem Beginn
     * @param startDate
     * 
     * @return Vektor mit Survey-Objekten
     */
    public Vector<Survey> findSurveyByStartDate(Date startDate) {
        Connection con = DBConnection.connection();
        Vector<Survey> result = new Vector<Survey>();
        
        try {
        	Statement stmt = con.createStatement();


        	ResultSet rs = stmt.executeQuery("SELECT * FROM survey" 
        	+ "WHERE startDate= '" + startDate + "'");
        	//Für jeden Eintrag im Suchergebnis wird ein Survey-Objekt erstellt


        	while(rs.next()) {
        		Survey s = new Survey();
        		s.setId(rs.getInt("id"));
        		s.setStartDate(rs.getDate("startDate"));
        		s.setEndDate(rs.getDate("endDate"));
        		s.setGroupFK(rs.getInt("groupFK"));
        		
        		//Hinzufügen des neuen Objekts zum Ergebnisvektor
        		result.addElement(s);
        	}
        }
        	catch(SQLException e2) {
        		e2.printStackTrace();
        	}
        	//RÃ¯Â¿Â½ckgabe des Ergebnisvektors
        	return result;
        }
    
    /**
     * Auslesen der Survey-Objekte mit gegebenem Ende 
     * @param endDate 
     * @return Vektor mit Survey-Objekten
     */
    public Vector<Survey> findSurveyByEndDate(Date endDate) {
        Connection con = DBConnection.connection();
        Vector<Survey> result = new Vector<Survey>();
        
        try {
        	Statement stmt = con.createStatement();

        	ResultSet rs = stmt.executeQuery("SELECT * FROM survey" + "WHERE endDate= '" + endDate + "'");
        	//Für jeden Eintrag im Suchergebnis wird ein Survey-Objekt erstellt

        	while(rs.next()) {
        		Survey s = new Survey();
        		s.setId(rs.getInt("id"));
        		s.setStartDate(rs.getDate("startDate"));
        		s.setEndDate(rs.getDate("endDate"));
        		s.setGroupFK(rs.getInt("groupFK"));
        		
        		//Hinzufügen des neuen Objekts zum Ergebnisvektor
        		result.addElement(s);
        	}
        } catch(SQLException e2) {
        	e2.printStackTrace();
        }
        //Rückgabe des Ergebnisvektors
        return result;
    }
    
    /**
     * Auslesen der Survey-Objekte mit gegebener GroupFK (FremdschlÃ¯Â¿Â½ssel)
     * @param groupFK
     * @return Vektor mit Survey-Objekten
     */
    public Vector<Survey> findSurveyByGroupFK(int groupFK) {
    	Connection con = DBConnection.connection();
    	Vector<Survey> result = new Vector<Survey>();
    	
    	try {
    		Statement stmt = con.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM survey "
    				+ "WHERE survey.groupFK=" + groupFK);
    		
    		//Für jeden Eintrag im Suchergebnis wird ein Survey-Objekt erstellt
    		while(rs.next()) {
    			Survey s = new Survey();
    			s.setId(rs.getInt("id"));
    			s.setStartDate(rs.getDate("startDate"));
    			s.setEndDate(rs.getDate("endDate"));
    			s.setGroupFK(rs.getInt("groupFK"));
    			
    			
    			//Hinzufügen des Objekts zum Ergebnisvektor
    			result.addElement(s);
    		}
    	} catch(SQLException e2) {
    		e2.printStackTrace();
    	}
    	//Rückgabe des Ergebnisvektors
    	return result;
        
    }
    
    /**
     * Löschen einer Umfrage durch den GroupFK(Fremdschlüssel)
     * @param groupFK
     */
    
    public void deleteSurveyByGroupFK(int groupFK) {
    	Connection con = DBConnection.connection();
    	
    	try {
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate("DELETE FROM survey" + "WHERE survey.groupFK=" + groupFK);
    	}
    	catch(SQLException e2) {
    		e2.printStackTrace();
    	}
    }
    
    /**
     * @param personFK 
     * @return Vektor mit Survey-Objekten eines Erstellers
     */
    public Vector<Survey> findSurveyByPersonFK(int personFK) {
        Connection con = DBConnection.connection();
        Vector<Survey> result = new Vector<Survey>();
        
        try {
        	Statement stmt = con.createStatement();
        	
        	ResultSet rs = stmt.executeQuery("SELECT survey.id, survey.startDate, survey.endDate, survey.groupFK "
        			+ "FROM survey INNER JOIN popcorns.businessownership "
        			+ "ON survey.id = businessownership.id AND businessownership.personFK= '" + personFK + "'");
        	
        	//Für jeden Eintrag im Suchergebnis wird ein Survey-Objekt zugeordnet
        	while(rs.next()) {
        		Survey s = new Survey();
        		s.setId(rs.getInt("id"));
        		s.setStartDate(rs.getDate("startDate"));
        		s.setEndDate(rs.getDate("endDate"));
        		s.setGroupFK(rs.getInt("groupFK"));
        		
        		
        		
        		//Hinzufügen des neuen Objekts zum Ergebnisvektor
        		result.addElement(s);
        	}
        }
        catch(SQLException e2) {
        	e2.printStackTrace();
        }
        return result;
        
    }
    
    

}