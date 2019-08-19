/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nigel_Phan_17983161;

/**
 *
 * @author khj8681
 */
import java.util.List;

public class MovieSession implements Comparable<MovieSession> {

    private final String movieName;
    private final char rating;
    private final Time sessionTime;
    private final SeatReservation[][] sessionSeats = new SeatReservation[NUM_ROWS][NUM_COLS];
    public static int NUM_ROWS = 8;
    public static int NUM_COLS = 6;

    public MovieSession(String movieName, char rating, Time sessionTime) {
        this.movieName = movieName;
        this.rating = rating;
        this.sessionTime = sessionTime;
    }

    public static int convertRowtoIndex(char rowLetter) {
        return (int) (rowLetter - 65);
    }

    public static char convertIndextoRow(int rowIndex) {
        return (char) (rowIndex + 65);
    }

    public char getRating() {
        return rating;
    }

    public String getMovieName() {
        return movieName;
    }

    public Time getSessionTime() {
        return sessionTime;
    }

    public SeatReservation getSeat(char row, int col) {
        return sessionSeats[this.convertRowtoIndex(row)][col];
    }

    public boolean isSeatAvailable(char row, int col) {
        //Check if seat is available
        if (sessionSeats != null && row >= 0 && col >= 0) {

            if (sessionSeats != null) {
                System.out.println("This seat is taken!");
                return false;
            }
        }
        //Check if row and column are out of bounds
        if (row > NUM_ROWS || row < NUM_ROWS || col > NUM_COLS || col < NUM_COLS) {
            System.out.println("This seat doesn't exist!");
            return false;
        }
        return true;
    }

    public boolean applyBookings(List<SeatReservation> reservations) {

        int childCount = 0;
        int adultCount = 0;
        int elderlyCount = 0;

        if (reservations.isEmpty()) {
            return false;
        } else {
       
            for (int i = 0; i < reservations.size(); i++) 
            {
                int x = convertRowtoIndex(reservations.get(i).getRow());
                int y = reservations.get(i).getCol();

                this.sessionSeats[x][y] = reservations.get(i);

                //Count child reservations
                if (reservations.get(i) instanceof ChildReservation) {
                    childCount++;
                } //Count adult/elderly reservations
                else if (reservations.get(i) instanceof ElderlyReservation) {
                    elderlyCount++;
                }
                else if (reservations.get(i) instanceof AdultReservation) {
                    adultCount++;
                }
            }
            //Reject children bookings in R movies
            if (this.rating == 'R' && childCount > 0) {
                System.out.println("Children cannot book into R rated movies!");
                return false;
            } //If no parents are with children, reject reservation
            else if (this.rating == 'M' && childCount > 0 && adultCount == 0 && elderlyCount == 0) {
                System.out.println("Adults MUST supervise children in M rated movies!");
                return false;
            } 
            return true;
        }
    }

    public void printSeats() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (sessionSeats[i][j] == null) {
                    System.out.print("|_|");
                } else if (sessionSeats[i][j] instanceof AdultReservation) {
                    System.out.print("|A|");
                } else if (sessionSeats[i][j] instanceof ElderlyReservation) {
                    System.out.print("|E|");
                } else if (sessionSeats[i][j] instanceof ChildReservation) {
                    System.out.print("|C|");
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return movieName + " (" + rating + ") - " + sessionTime;
    }

    @Override
    public int compareTo(MovieSession other) {

        //Compare session time for earliest
        int result = this.sessionTime.compareTo(other.sessionTime);

        if (result != 0) {
            return result;
        } //Compare by movie name if session time is the same
        else if (result == 0) {
            result = this.movieName.compareTo(other.movieName);
        }
        return result;
    }
}
