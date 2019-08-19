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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class MovieBookingGUI extends JPanel implements ActionListener, ListSelectionListener {

    private final JButton[][] seatingButtons;
    private final JLabel titleLabel;
    private final JRadioButton adultRadioButton, elderlyRadioButton, childRadioButton;
    private final JList movieList;
    private final DefaultListModel model;
    private final JCheckBox complementaryBox;
    private final JButton exitButton, newButton, bookButton;
    private final JScrollPane pane;
    private final int TOTAL_ROWS = 8;
    private final int TOTAL_COLS = 6;
    private MovieSession currentMovie;
    private final ArrayList<MovieSession> movies;
    private final ArrayList<SeatReservation> currentReservation = new ArrayList<>();
    private String customerKind = "Adult";
    private boolean isComplementary = false;

    public MovieBookingGUI(ArrayList<MovieSession> movieSessions) {
        super(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(8, 6));
        centerPanel.setPreferredSize(new Dimension(625, 500));

        this.movies = movieSessions;

        seatingButtons = new JButton[TOTAL_ROWS][TOTAL_COLS];
        //Buttons for movie seats
        for (int i = 0; i < TOTAL_ROWS; i++) {
            for (int j = 0; j < TOTAL_COLS; j++) {
                seatingButtons[i][j] = new JButton("[" + MovieSession.convertIndextoRow(i) + "]" + "[" + j + "]");
                seatingButtons[i][j].addActionListener(this);
                seatingButtons[i][j].setBackground(Color.LIGHT_GRAY);
                seatingButtons[i][j].setForeground(Color.BLACK);
                centerPanel.add(seatingButtons[i][j]);
            }
        }
        add(centerPanel, BorderLayout.CENTER);

        //Title of GUI
        titleLabel = new JLabel("Movie Booking Machine");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));

        JPanel northPanel = new JPanel();
        northPanel.add(titleLabel);
        add(northPanel, BorderLayout.NORTH);

        //List of Movies
        model = new DefaultListModel();
        //Loop through MovieSession objects of movies and output them
        for (int i = 0; i < movies.size(); i++) {
            model.addElement(movieSessions.get(i));
        }

        movieList = new JList(model);
        movieList.setFixedCellWidth(450);
        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Select the first movie on the list by default
        this.movieList.setSelectedIndex(0);
        pane = new JScrollPane(movieList);
        add(pane, BorderLayout.EAST);
        //Add the ListSeletionListener to the JList of movies
        movieList.addListSelectionListener(this);

        // list.setPreferredSize(new Dimension(50,500));
        //Exit booking system button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        //New bookings button
        newButton = new JButton("New");
        newButton.addActionListener(this);
        //Booking button
        bookButton = new JButton("Book");
        bookButton.addActionListener(this);

        //Adult booking button
        adultRadioButton = new JRadioButton("Adult", true);
        adultRadioButton.addActionListener(this);
        // Elderly booking button
        elderlyRadioButton = new JRadioButton("Elderly");
        elderlyRadioButton.addActionListener(this);
        //Child booking button
        childRadioButton = new JRadioButton("Child");
        childRadioButton.addActionListener(this);
        //Complementary checkbox
        complementaryBox = new JCheckBox("Complementary");

        ButtonGroup group = new ButtonGroup();
        group.add(adultRadioButton);
        group.add(elderlyRadioButton);
        group.add(childRadioButton);

        JPanel southPanel = new JPanel();
        southPanel.add(adultRadioButton);
        southPanel.add(elderlyRadioButton);
        southPanel.add(childRadioButton);
        southPanel.add(complementaryBox);
        southPanel.add(exitButton);
        southPanel.add(newButton);
        southPanel.add(bookButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        //Get the value of the selected movie in the list
        int index = this.movieList.getSelectedIndex();
        currentMovie = this.movies.get(index);

        for (int row = 0; row < MovieSession.NUM_ROWS; row++) {
            for (int col = 0; col < MovieSession.NUM_COLS; col++) {
                if (this.currentMovie.getSeat((char) (row + 65), col) instanceof ChildReservation) {
                    this.seatingButtons[row][col].setBackground(Color.YELLOW);
                    this.seatingButtons[row][col].setEnabled(false);
                } else if (this.currentMovie.getSeat((char) (row + 65), col) instanceof ElderlyReservation) {
                    this.seatingButtons[row][col].setBackground(Color.WHITE);
                    this.seatingButtons[row][col].setEnabled(false);

                } else if (this.currentMovie.getSeat((char) (row + 65), col) instanceof AdultReservation) {
                    this.seatingButtons[row][col].setBackground(Color.BLUE);
                    this.seatingButtons[row][col].setEnabled(false);
                } else {
                    this.seatingButtons[row][col].setBackground(Color.LIGHT_GRAY);
                    this.seatingButtons[row][col].setForeground(Color.BLACK);
                    this.seatingButtons[row][col].setEnabled(true);
                }
            }
        }
    }

    //Helper method which checks children bookings that were not made successfully
    private void unvalidBookings(ArrayList<SeatReservation> currentReservation) {
        int childTickets = 0;

        for (SeatReservation seat : currentReservation) {
            if (seat instanceof ChildReservation) {
                childTickets++;
            }
        }
        if (childTickets > 1) {
            JOptionPane.showMessageDialog(null, "Cannot book child in R rated Movies or Unsupervised in M movies", childTickets + " Tickets Not Booked", JOptionPane.ERROR_MESSAGE);
        } else if (childTickets == 0) {
            JOptionPane.showMessageDialog(null, "Cannot book child in R rated Movies or Unsupervised in M movies", "0 Tickets Booked", JOptionPane.ERROR_MESSAGE);
        } else if (childTickets == 1) {
            JOptionPane.showMessageDialog(null, "Cannot book child in R rated Movies or Unsupervised in M movies", "1 Ticket Not Booked", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Helper method which changes the colour of the seats if booked succesffully
    private void validateSeats(ArrayList<SeatReservation> currentReservation) {
        for (SeatReservation seat : currentReservation) {
            if (seat instanceof ChildReservation) {
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setBackground(Color.YELLOW);
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setForeground(Color.LIGHT_GRAY);
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setEnabled(false);
            } else if (seat instanceof ElderlyReservation) {
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setBackground(Color.WHITE);
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setForeground(Color.LIGHT_GRAY);
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setEnabled(false);
            } else if (seat instanceof AdultReservation) {
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setBackground(Color.BLUE);
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setForeground(Color.LIGHT_GRAY);
                this.seatingButtons[(int) (seat.getRow() - 65)][seat.getCol()].setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        float cost = 0.00f;

        //Exit the program 
        if (source == exitButton) {
            System.exit(0);
        } else if (source == bookButton) {
            //Check if bookings were successful or unsuccessful
            int index = movieList.getSelectedIndex();
            currentMovie = movies.get(index);

            if (this.currentMovie.applyBookings(this.currentReservation)) {
                //Once the bookings have been made, calculate the total ticket price                      
                for (SeatReservation seat : currentReservation) {
                    cost += seat.getTicketPrice();
                }
                JOptionPane.showMessageDialog(null, String.format("Total Ticket Cost is: $%.2f", cost), currentReservation.size() + " Tickets Booked", JOptionPane.INFORMATION_MESSAGE);
                //Check if seats are available and who booked them
                validateSeats(this.currentReservation);
                //Clear the reservation list
                this.currentReservation.clear();
            } //If booking is unsuccessful
            else if (currentMovie.applyBookings(currentReservation) == false) {
                //Call helper method which counts how many tickets wree not booked and output error message box
                unvalidBookings(this.currentReservation);
                //Clear the reservation list
                this.currentReservation.clear();
            }
        } else if (source == newButton) {
            currentReservation.clear();
            for (int i = 0; i < MovieSession.NUM_ROWS; i++) {
                for (int j = 0; j < MovieSession.NUM_COLS; j++) {
                    this.seatingButtons[i][j].setBackground(Color.LIGHT_GRAY);
                    this.seatingButtons[i][j].setForeground(Color.BLACK);
                    this.seatingButtons[i][j].setEnabled(true);
                }
            }
        } else if (source == childRadioButton) {
            this.customerKind = "Child";
        } else if (source == elderlyRadioButton) {
            this.customerKind = "Elderly";
        } else if (source == adultRadioButton) {
            this.customerKind = "Adult";
        } else { //48 seatbutton area 
            SeatReservation seat;
            for (int i = 0; i < this.TOTAL_ROWS; i++) {
                for (int j = 0; j < this.TOTAL_COLS; j++) {
                    if (source == this.seatingButtons[i][j]) {
                        this.seatingButtons[i][j].setEnabled(false);

                        //Check Reservation and customer type
                        if (this.customerKind.equals("Child")) {
                            seat = new ChildReservation((char) (i + 65), j);

                            if (complementaryBox.isSelected()) {
                                this.isComplementary = true;
                                seat.setComplementary(isComplementary);
                            } else {
                                this.isComplementary = false;
                                seat.setComplementary(isComplementary);
                            }
                            this.currentReservation.add(seat);

                        } else if (this.customerKind.equals("Adult")) {
                            seat = new AdultReservation((char) (i + 65), j);

                            if (complementaryBox.isSelected()) {
                                this.isComplementary = true;
                                seat.setComplementary(isComplementary);
                            } else {
                                this.isComplementary = false;
                                seat.setComplementary(isComplementary);
                            }
                            this.currentReservation.add(seat);

                        } else if (this.customerKind.equals("Elderly")) {
                            seat = new ElderlyReservation((char) (i + 65), j);

                            if (complementaryBox.isSelected()) {
                                this.isComplementary = true;
                                seat.setComplementary(isComplementary);
                            } else {
                                this.isComplementary = false;
                                seat.setComplementary(isComplementary);
                            }
                            this.currentReservation.add(seat);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        ArrayList<MovieSession> movieSessions = new ArrayList<>();

        Time time1 = new Time(15, 30, 00);
        Time time2 = new Time(16, 45, 00);
        Time time3 = new Time(18, 20, 00);
        Time time4 = new Time(9, 50, 00);
        Time time5 = new Time(9, 50, 00);
        //Movie objects
        MovieSession movie1 = new MovieSession("Ted", 'R', time1);
        MovieSession movie2 = new MovieSession("Harry Potter and the Philosopher's Stone", 'M', time2);
        MovieSession movie3 = new MovieSession("Frozen", 'G', time3);
        MovieSession movie4 = new MovieSession("The Chronicles of Narnia: The Lion, the Witch and the Wardrobe", 'M', time4);
        MovieSession movie5 = new MovieSession("Balto", 'G', time5);

        //Add MovieSession movie objects into arraylist
        movieSessions.add(movie1);
        movieSessions.add(movie2);
        movieSessions.add(movie3);
        movieSessions.add(movie4);
        movieSessions.add(movie5);

        //Sort the movies
        Collections.sort(movieSessions);

        MovieBookingGUI moviePanel = new MovieBookingGUI(movieSessions);

        //TODO Pass the sorted movies to teh MovieBookingGUI constructor
        JFrame frame = new JFrame("Movie Bookings"); //create frame to hold our JPanel subclass	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(moviePanel);  //add instance of MyGUI to the frame
        frame.pack(); //resize frame to fit our Jpanel
        //Position frame on center of screen 
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        //show the frame	
        frame.setVisible(true);
    }
}
