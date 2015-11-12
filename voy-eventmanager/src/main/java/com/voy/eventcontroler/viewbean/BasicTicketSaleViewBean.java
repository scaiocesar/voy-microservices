package com.voy.eventcontroler.viewbean;

import java.util.Date;

/**
 * Created by Bozo on 09/11/2015.
 */
public class BasicTicketSaleViewBean {

    private String barcode;
    private String name;
    private String status;
    private Date saleDate;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
