package de.hdm.SoPra_WS1920.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.SoPra_WS1920.shared.bo.Screening;
import de.hdm.SoPra_WS1920.shared.bo.Survey;
import de.hdm.SoPra_WS1920.shared.bo.SurveyEntry;


/**
 * Mapper-Klasse, die <code>SurveyEntry</code>-Objekte auf relationale Datenbank abbildet.
 * Anhand von den Methoden k�nnen Objekte gesucht, erzeugt, bearbeitet und gel�scht werden. 
 * Objekte k�nnen in DB-Strukturen umgewandelt werden und DB-Strukturen in Objekte.
 * 
 * @author shila
 */
public class SurveyEntryMapper {

    /**
     * Die Klasse SurveyEntryMapper wird nur einmal instanziiert (Singleton-Eigenschaft).
     * Die folgende Variable ist durch den Bezeichner <code>static</code> nur einmal f�r 
     * alle Instanzen der Klasse vorhanden. Die einzige Instanz dieser Klasse wird darin gespeichert. 
     */
	private static SurveyEntryMapper surveyEntryMapper = null;
     
	/**
	 * Gesch�tzter Konstruktor, der verhindert, dass mit dem new-Operator
	 * neue Instanzen der Klasse erstellt werden.
	 */
    protected SurveyEntryMapper() {
    }
    
    /**
     * Folgende statische Methode sichert die Singleton-Eigenschaft.
     * Es wird daf�r gesorgt, dass nur eine einzige Instanz von
     * <code>SurveyEntryMapper</code> existiert.
     * SurveyEntryMapper wird durch den Aufruf dieser statischen Methode instanziiert, 
     * nicht durch den new-Operator.
     * Aufruf der Methode durch: <code>SurveyEntryMapper.surveyEntryMapper()</code>
     * 
     * @return <code>SurveyEntryMapper</code>-Objekt
     */
    public static SurveyEntryMapper surveyEntryMapper() {
    	if(surveyEntryMapper == null) {
    		surveyEntryMapper = new SurveyEntryMapper();		
    	}
    	return surveyEntryMapper;  	
    }
    
    /**
     * @param id (Prim�rschl�ssel-Attribut)
     * @return SurveyEntry-Objekt, das dem �bergebenen Schl�ssel entspricht,
     * null bei nicht vorhandenem DB-Tupel
     */
    public SurveyEntry findSurveyEntryByID(int id) {
        Connection con = DBConnection.connection();
        
        try {
        	
        	Statement stmt = con.createStatement();
        	
        	ResultSet rs = stmt.executeQuery("SELECT * FROM surveyentry" + "WHERE surveyentry.bo_id=" + id);
        	
        	//Es wird gepr�ft, ob ein Ergebnis vorliegt
        	if(rs.next()) {
        		//Ergebnis-Tupel in Objekt umwandeln
        		SurveyEntry surveyEntry = new SurveyEntry();
        		surveyEntry.setSurveyFK(rs.getInt("surveyFK"));
        		surveyEntry.setScreeningFK(rs.getInt("screeningFK"));
        		
        		return surveyEntry;
        	}
        	
        } catch(SQLException e2) {
        	e2.printStackTrace();
        }
        
        return null;
        
    }


    /**
     * Einf�gen eines <code>SurveyEntry</code>-Objekts in die DB.
     * Pr�fung und ggf. Korrektur des Prim�rschl�ssels
     * @param surveyEntry das zu speichernde Objekt
     * @return das �bergebene Objekt, mit ggf. korrigierter <code>id</code>.
     */
    public SurveyEntry insertSurveyEntry(SurveyEntry surveyEntry) {
    	Connection con = DBConnection.connection();
    	
    	try {
    		Statement stmt1 = con.createStatement();
    		Statement stmt2 = con.createStatement();
    		
    		stmt1.executeUpdate("INSERT INTO businessobject(bo_id, creationTimeStamp)" +
    		"VALUES ('"
    		+ surveyEntry.getId()
    		+ "','"
    		+ surveyEntry.getCreationTimestamp() + "')");
    		
    		stmt2.executeUpdate("INSERT INTO surveyentry(bo_id, surveyFK, screeningFK, creationTimeStamp)" +
    		"VALUES ('"
    		+ surveyEntry.getId()
    		+ "','"
    		+ surveyEntry.getSurveyFK()
    		+ "','"
    		+ surveyEntry.getScreeningFK()
    		+ "','"
    		+ surveyEntry.getCreationTimestamp() + "')");
    	}
    	catch(SQLException e2) {
    		e2.printStackTrace();
    	}
    	 return surveyEntry;
        
    }

    /**
     * @param surveyEntry 
     * @return
     */
     
    public SurveyEntry updateSurveyEntry(SurveyEntry surveyEntry) {
    	Connection con = DBConnection.connection();
    	
    	try {
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate("UPDATE surveyentry" + "SET surveyFK=\'" + surveyEntry.getSurveyFK() 
    			+ "\", " + "screeningFK=\'" + surveyEntry.getScreeningFK() + "\", " + "WHERE bo_id=" + surveyEntry.getId());
    	}
    	
    	catch(SQLException e2) {
    		e2.printStackTrace();
    	}
        return surveyEntry;
    }
    

    /**
     * @param surveyEntry 
     * @return
     */
    public void deleteSurveyEntry(SurveyEntry surveyEntry) {
        Connection con = DBConnection.connection();
        
        try {
        	Statement stmt1 = con.createStatement();
        	Statement stmt2 = con.createStatement();
        	
        	stmt1.executeUpdate("DELETE FROM surveyentry WHERE bo_id=" + surveyEntry.getId());
        	stmt2.executeUpdate("DELETE FROM businessobject WHERE bo_id=" + surveyEntry.getId());
        }
        catch(SQLException e2) {
        	e2.printStackTrace();
        }
    }

    /**
     * Auslesen aller Umfrageeintr�ge durch Fremdschl�ssel (screeningFK) gegebene Spielzeiten.
     * @param screeningFK 
     * @return
     */
    public Vector<SurveyEntry> findSurveyEntryByScreeningFK(int screeningFK) {
        Connection con = DBConnection.connection();
        Vector<SurveyEntry> result = new Vector<SurveyEntry>();
        
        try {
        	Statement stmt = con.createStatement();
        	
        	ResultSet rs = stmt.executeQuery("SELECT * FROM surveyentry" + "WHERE screeningFK=" + screeningFK);
        	
        	while(rs.next()) {
        		SurveyEntry surveyEntry = new SurveyEntry();
        		surveyEntry.setSurveyFK(rs.getInt("surveyFK"));
        		surveyEntry.setScreeningFK(rs.getInt("screeningFK"));
        		
        		result.addElement(surveyEntry);
        	}
        	
        }
        catch(SQLException e2) {
        	e2.printStackTrace();
        }
        return result;
    }

    /**
     * L�schen eines Umfrageeintrags durch die Spielzeit
     * @param screeningFK 
     */
    public void deleteSurveyEntryByScreeningFK(int screeningFK) {
    	Connection con = DBConnection.connection();
    	
    	try {
    		Statement stmt = con.createStatement();
    		
    		stmt.executeUpdate("DELETE FROM surveyentry" + "WHERE screeningFK=" + screeningFK);
    	}
    	catch(SQLException e2) {
    		e2.printStackTrace();
    	}
        
    }


    /**
     * Auslesen aller Umfrageeintr�ge durch Fremdschl�ssel (surveyFK) gegebene Umfragen
     * @param surveyFK
     * @return Vektor mit Umfrageeintrag-Objekten
     */
    public Vector<SurveyEntry> findSurveyEntryBySurveyFK(int surveyFK) {
    	Connection con = DBConnection.connection();
    	Vector<SurveyEntry> result = new Vector<SurveyEntry>();
    	
    	try {
    		Statement stmt = con.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM surveyentry" + "WHERE surveyFK=" + surveyFK);
    		
    		while(rs.next()) {
    			SurveyEntry surveyEntry = new SurveyEntry();
    			surveyEntry.setScreeningFK(rs.getInt("screeningFK"));
    			surveyEntry.setSurveyFK(rs.getInt("surveyFK"));
    			
    			result.addElement(surveyEntry);
    		}
    	}
    	catch(SQLException e2) {
    		e2.printStackTrace();
    	}
    	return result;

    }
    

    /**
     * L�schen eines Umfrageeintrags durch (Fremdschl�ssel) der Angabe der UmfrageID
     * @param survey 
     * @return
     */
    public void deleteSurveyEntryBySurveyFK(Survey surveyFK) {
        Connection con = DBConnection.connection();
        
        try {
        	Statement stmt = con.createStatement();
        	
        	stmt.executeUpdate("DELETE FROM surveyentry" + "WHERE surveyFK=" + surveyFK);
        }
        catch(SQLException e2) {
        	e2.printStackTrace();
        }
    }

}