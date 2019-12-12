package de.hdm.SoPra_WS1920.server.db;

import java.util.Vector;
import java.sql.*;


import de.hdm.SoPra_WS1920.shared.bo.Movie;
import de.hdm.SoPra_WS1920.shared.bo.Person;

/**
 * @author David Flattich
 * 
 * 
 * Mit Hilfe der MapperKlasse <code>MovieMapper</code> werden Movie-Objekte auf eine relationale Datenbank abgebildet.
 * Durch das implementieren der Methoden k�nnen Movie-Objekte gesucht, erzeugt, modifiziert und
 * gel�scht werden.
 * 
 */
public class MovieMapper {

	/**
     * Durch einem sogeannten <b>Singleton<b> kann die Klasse MovieMapper nur einmal instantiiert werden.
	 * Mit Hilfe von <code>static</code> wird dies umgesetzt.
     */
    private static MovieMapper movieMapper = null;

    /**
     * Ein gesch?tzter Konstruktor der weitere Instanzierungen von MovieMapper Objekten verhindert.
     */
    protected MovieMapper() {
 
    }

    /**
	 * Stellt die Singeleton-Eigenschaft der Mapperklasse sicher
	 * Sie daf?r sorgt, dass nur eine einzige Instanz von <code>MovieMapper</code> existiert.
	 * @return Sie gibt den MovieMapper zur?ck.
	 */
    
    public static MovieMapper moviemapper() {
        if (movieMapper == null) {
        	movieMapper = new MovieMapper();
        }
        return movieMapper;
    }

    /**
     * @param Movie
     *           Prim?rschl?sselattribut (->DB)
	 * @return Movie-Objekt, das dem ?bergebenen Schl?ssel entspricht, null bei
	 *         nicht vorhandenem DB
	 */
    
    
    /*
	 * =============================================================================================
	 * Beginn: Standard-Mapper-Methoden. Innerhalb dieses Bereichs werden alle Methoden aufgez�hlt, die
	 * in allen Mapper-Klassen existieren.
	 * 
	 */	
    public Movie findMovieByID(int movieID) {
    	Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM movie " + "WHERE bo_id= " + movieID);
			
			if(rs.next()) {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setGenre(rs.getString("genre"));
				m.setDescription(rs.getString("description"));
				return m;
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    
    /**
	 * Methode, die das Anlegen eines Movie-Objekts erm�glicht
	 * @param person
	 */
    
    
    public Movie insertMovie(Movie movie) {
    	Connection con = DBConnection.connection();

    	try {
    		con.setAutoCommit(false);
    		Statement stmt = con.createStatement();
        	
    	    ResultSet rs = stmt.executeQuery("SELECT MAX(bo_id) AS maxid "
    	          + "FROM businessobject ");

    	    if (rs.next()) {
    	     
    	    movie.setId(rs.getInt("maxid") + 1);
    		Statement stm1 = con.createStatement();
    		Statement stm2 = con.createStatement();
    		
    		stm1.executeUpdate("INSERT INTO businessobject (bo_id, creationTimeStamp) VALUES ('"
								+movie.getId()
								+"', '"+movie.getCreationTimestamp()
								+"')");
			stm2.executeUpdate("INSERT INTO movie (bo_id, name, genre, description, creationTimeStamp) VALUES ('"
								+movie.getId()
								+"', '"+movie.getName()
								+"', '"+movie.getGenre()
								+"', '"+movie.getDescription()
								+"', '"+movie.getCreationTimestamp()
								+"')");
			
		}con.setAutoCommit(true);
    	}
			catch(SQLException exc) {
				exc.printStackTrace();
			
			}
        return movie;
    }

    /**
	 * Methode, die das Updaten eines Movie-Objekts in der Datenbank erm�glicht	
	 * @param person
	 */
    
    public Movie updateMovie(Movie movie) {
    	Connection con = DBConnection.connection();

    	try {
    		con.setAutoCommit(false);
    		Statement stmt = con.createStatement();
    		stmt.executeUpdate("UPDATE movie Set name='"+movie.getName()
    				+"', genre='"+movie.getGenre()
    				+"', description='"+movie.getDescription()
    				+"' Where bo_id="+movie.getId());
    		con.setAutoCommit(true);
    	}
    		catch(SQLException exc) {
    			exc.printStackTrace();
    			}
        return movie;
    }

    /**
	 * Methode, die das Loeschen eines Movie-Objekts aus der Datenbank erm�glicht
	 * @param person
	 */
    
    public void deleteMovie(Movie movie) {
    	Connection con = DBConnection.connection();
    	
    	try {
			Statement stm1 = con.createStatement();
			Statement stm2 = con.createStatement();
			
			stm1.executeUpdate("Delete from movie Where bo_id = "+movie.getId());
			stm2.executeUpdate("Delete from businessobject Where bo_id = "+movie.getId());
			
		}catch(SQLException e2) {
			e2.printStackTrace();
		}
    }

    
    /* Ende: Standard-Mapper-Methoden
 	 * ================================================================================================
 	 * Beginn: Foreign Key-Mapper-Methoden
 	 */
    /**
     * @param id 
     * @return
     */
    public Vector<Movie> findMovieByPersonFK(int personFK) {
    	Connection con = DBConnection.connection();
		Vector<Movie> result = new Vector<Movie>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT movie.bo_id, movie.name, movie.genre, movie.description FROM movie\n" + 
					"INNER JOIN popcorns.businessownership\n" + 
					"ON businessownership.bo_id = movie.bo_id\n" + 
					"AND businessownership.personFK = " + personFK);
		
			while (rs.next()) {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setGenre(rs.getString("genre"));
				m.setDescription(rs.getString("description"));
				result.add(m);
				
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
        return result;
    }

    /**
     * @param person 
     * @return
     */
    /*public void deleteMovieByPersonFK(Person person) {
       /** Umsetzung �berhaupt n�tig? Movies einer Person sollten in der Regel nicht gel�scht werden
        return null;
    }
    
    
     /* Ende:  Foreign Key-Mapper-Methoden
     * ================================================================================================
     * Beginn: Spezifische Business Object Methoden
	 */	
    	
    // Find-All Methode zum Abrufen aller Movie-Objekte
    public Vector<Movie> findAll() {
        Connection con = DBConnection.connection();
        Vector<Movie> result = new Vector<Movie>();

        try {
          Statement stmt = con.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT * FROM movie "
              + " ORDER BY bo_id");

          while (rs.next()) {
            Movie m = new Movie();
            m.setId(rs.getInt("bo_id"));
            m.setName(rs.getString("name"));
            m.setGenre(rs.getString("genre"));
            m.setDescription(rs.getString("description"));

            // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
            result.addElement(m);
          }
        }
        catch (SQLException e2) {
          e2.printStackTrace();
        }

        // Ergebnisvektor zur�ckgeben
        return result;
      }
    /**
     * @param name 
     * @return
     */
    public Vector<Movie> findMovieByName(String name) {
    	Connection con = DBConnection.connection();
		Vector<Movie> result = new Vector<Movie>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM movie Where name='" +name+"'");
		
			while (rs.next()) {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setGenre(rs.getString("genre"));
				m.setDescription(rs.getString("description"));
				result.add(m);
				
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
        return result;
    }

    /**
     * @param name 
     * @return
     */
    /**public void deleteMovieByName(String name) {
    	Connection con = DBConnection.connection();
		
		try {
			Statement stm1 = con.createStatement();
			
			stm1.executeUpdate("Delete from movie Where name='" +name+"'");
				
						
		}
		catch (SQLException e) {
			e.printStackTrace();
		}        
    }
	Umsetzung �berhaupt n�tig?/**
	
	
    /**
     * @param genre 
     * @return
     */
    public Vector<Movie> findMovieByGenre(String genre) {
    	Connection con = DBConnection.connection();
		Vector<Movie> result = new Vector<Movie>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM movie Where genre='" +genre+"'");
		
			while (rs.next()) {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setGenre(rs.getString("genre"));
				m.setDescription(rs.getString("description"));
				result.add(m);
				
			}			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
        return result;
    }

    

}