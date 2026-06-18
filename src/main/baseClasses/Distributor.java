package main.baseClasses;

import java.time.LocalDateTime;

public class Distributor extends Client
{

    private double distributionPrice;
    private LocalDateTime lastShipmentDate;
    private String licenseNumber;

    public Distributor(int clientID, String name, String licenseNumber, double distributionPrice )  //constructor
    {
        super(clientID, name);
        this.licenseNumber =licenseNumber;
        this.distributionPrice = distributionPrice;
        this.lastShipmentDate = null;
    }

    public void recordNewShipment()     //sets present date as last shipment date
    {
        this.lastShipmentDate = LocalDateTime.now();
    }

    // Getters & Setters

    public String getLicenseNumber()
    {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber)
    {
        this.licenseNumber = licenseNumber;
    }

    public LocalDateTime getLastShipmentDate()
    {
        return lastShipmentDate;
    }

    public double getDistributionPrice()
    {
        return distributionPrice;
    }
    public void setDistributionPrice(double distributionPrice)
    {
        this.distributionPrice = distributionPrice;
    }
    private String printLastShipmentDate()
    {
        LocalDateTime date = this.getLastShipmentDate();

        if(date == null)
        {
            return null;
        }

        String minuteStr = (date.getMinute() < 10 ? "0" : "") + date.getMinute();
        String hourStr = (date.getHour() < 10 ? "0" : "") + date.getHour();

        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + " " + hourStr + ":" + minuteStr;
    }

    public String toString()
    {
        return super.toString() + " | License: " + licenseNumber + " | Last Shipment: " +
                                                                    (printLastShipmentDate() != null ? printLastShipmentDate(): "Never");
    }
}
