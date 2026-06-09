package main.baseClasses;

import java.time.LocalDateTime;

public class Distributor extends Client
{
    public enum Region
    {
        NORTH, SOUTH, CENTER
    }

    private Region distributionArea;
    private double distributionPrice;
    private LocalDateTime lastShipmentDate;
    private String licenseNumber;

    public Distributor(int clientID, String name, Region distributionArea, String licenseNumber, double distributionPrice )
    {
        super(clientID, name);
        this.distributionArea = distributionArea;
        this.distributionPrice = distributionPrice;
        this.lastShipmentDate = null;
    }

    public void recordNewShipment()
    {
        this.lastShipmentDate = LocalDateTime.now();
    }

    public Region getDistributionArea()
    {
        return distributionArea;
    }
    public void setDistributionArea(Region distributionArea)
    {
        this.distributionArea = distributionArea;
    }

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

    public String toString()
    {
        return super.toString() + " | License: " + licenseNumber + " | Area: " + distributionArea + " | Last Shipment: " +
                                                                    (lastShipmentDate != null ? lastShipmentDate: "Never");
    }
}
