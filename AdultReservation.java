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
public class AdultReservation extends SeatReservation {

    public float ticketPrice = 12.50f;

    //protected boolean complementary;
    public AdultReservation(char row, int col) {
        super(row, col);
    }

    @Override
    public void setComplementary(boolean complementary) {
        this.complementary = complementary;
        if (complementary == true) {
            ticketPrice = 0f;
        } else {
            this.getTicketPrice();
        }
    }

    @Override
    public float getTicketPrice() {
        if (complementary == true) {
            return 0;
        } else {
            return ticketPrice;
        }
    }
}
