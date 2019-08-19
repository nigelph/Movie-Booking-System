/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nigel_Phan_17983161;

/**
 *
 * @author Nigel
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieDriverClass {

    public static void main(String[] args) {

        List<MovieSession> movieList = new ArrayList<>();

        Time time = new Time(15, 30);
        Time time2 = new Time(16, 45);
        Time time3 = new Time(18, 20);
        Time time4 = new Time(9, 50);
        Time time5 = new Time(9, 50, 00);

        MovieSession movie1 = new MovieSession("Ted", 'R', time);
        MovieSession movie2 = new MovieSession("Harry Potter and the Philosopher's Stone", 'M', time2);
        MovieSession movie3 = new MovieSession("Frozen", 'G', time3);
        MovieSession movie4 = new MovieSession("The Chronicles of Narnia: The Lion, the Witch and the Wardrobe", 'M', time4);
        MovieSession movie5 = new MovieSession("Balto", 'G', time5);

        //Add MovieSession movie objects into arraylist
        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);
        movieList.add(movie4);
        movieList.add(movie5);

        //Sort the objects in the arraylist
        Collections.sort(movieList);

        //Print movie details and seats
        //System.out.println(movie1);
        //movie1.printSeats();
        System.out.println(movie2);
        movie2.printSeats();

//        System.out.println(movie3);
//        movie3.printSeats();
//        
//        System.out.println(movie4);
//        movie4.printSeats();
//        System.out.println(movie5);
//        movie5.printSeats();
        System.out.println("----------------------");

        ArrayList<SeatReservation> seats = new ArrayList<>();
        //Reservation objects
        AdultReservation adult = new AdultReservation('A', 5);
        ChildReservation child = new ChildReservation('B', 5);
        ElderlyReservation elderly = new ElderlyReservation('E', 0);

        //Manually make reservations by adding them to the seating
        //seats.add(adult);
        seats.add(child);
        //seats.add(c);

//        movie1.applyBookings(seats);
//        movie1.printSeats();
//        
//        movie2.applyBookings(seats);
//        movie2.printSeats();
//        
//        movie3.applyBookings(seats);
//        movie3.printSeats();
//        
//        movie4.applyBookings(seats);
//        movie4.printSeats();   
        movie5.applyBookings(seats);
        movie5.printSeats();
    }
}
