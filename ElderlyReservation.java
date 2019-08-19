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
public class ElderlyReservation extends AdultReservation {

    public float elderlyPrice;

    public ElderlyReservation(char row, int col) {
        super(row, col);
        elderlyPrice = this.ticketPrice;
    }

    @Override
    public void setComplementary(boolean complementary) {
        this.complementary = complementary;
        if (complementary == true) {
            this.ticketPrice = 0f;
        } else {
            this.getTicketPrice();
        }
    }

    @Override
    public float getTicketPrice() {

        if (complementary == true) {
            return 0;
        } else {
            return ticketPrice * 0.7f;
        }
    }
}
