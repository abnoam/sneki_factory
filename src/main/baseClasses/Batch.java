package main.baseClasses;

import java.time.LocalDateTime;

public class Batch
{
    private int quantity; // amount of products made in a daily batch
    private LocalDateTime expiryDate;

    public Batch(int quantity, LocalDateTime expiryDate)
    {
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public LocalDateTime getExpiryDate()
    {
        return expiryDate;
    }

    public String toString()
    {
        LocalDateTime date = this.getExpiryDate();

        return "Qty: "
                + quantity
                + " | Exp: "
                + date.getDayOfMonth() + "/"
                + date.getMonthValue() + "/"
                + date.getYear() + " "
                + date.getHour() + ":"
                + date.getMinute();
    }
}