package de.hdm.SoPra_WS1920.shared.bo;

import java.sql.Time;
import java.util.*;

/**
 * @author GianlucaBernert
 */
public class Screening extends Ownership {

    /**
     * Variables of the class Group
     */
    private int cinemaFK;
    private int movieFK;
    private Date date;
    private Time time;

    /**
     * Default constructor
     */
    public Screening() {
    }
  
    /**
     * @return cinemaFK
     */
    public int getCinemaFK() {
        return cinemaFK;
    }

    /**
     * @param cinemaFK 
     */
    public void setCinemaFK(int cinemaFK) {
        this.cinemaFK = cinemaFK;
    }

    /**
     * @return movieFK
     */
    public int getMovieFK() {
        return movieFK;
    }

    /**
     * @param movieFK 
     */
    public void setMovieFK(int movieFK) {
       this.movieFK = movieFK;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date 
     */
    public void setDate(Date date) {
       this.date = date;
    }

    /**
     * @return time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time 
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
   	 * Methode um eine textuelle Dastellung der jeweiligen Instanz zu erzeugen
   	 */
   	public String toString() {
   		return "ScreeningID #SC" + this.getId();
   	}
}