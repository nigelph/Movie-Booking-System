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
public abstract class SeatReservation {

    private final char row;
    private final int col;
    protected boolean complementary;

    public SeatReservation(char row, int col) {
        this.row = row;
        this.col = col;
    }

    public float getTicketPrice() {
        return 0;
    }

    public void setComplementary(boolean complementary) {
        complementary = this.complementary;
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
