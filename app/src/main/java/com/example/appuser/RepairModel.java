package com.example.appuser;

public class RepairModel {
    private String itm_headline, itm_price, itm_description;
//    private String rep_image;

    public RepairModel() {
    }
    public RepairModel(String itm_headline, String itm_description, String itm_price) {
        this.itm_headline = itm_headline;
//        this.rep_image = rep_image;
        this.itm_description = itm_description;
        this.itm_price = itm_price;
//        this.rep_price_to = rep_price_to;
    }

    public String getItm_headline() {
        return itm_headline;
    }

    public void setItm_headline(String itm_headline) {
        this.itm_headline = itm_headline;
    }

    public String getItm_description() {
        return itm_description;
    }

    public void setItm_description(String itm_description) {
        this.itm_description = itm_description;
    }

    public String getItm_price() {
        return itm_price;
    }

    public void setItm_price(String itm_price) {
        this.itm_price = itm_price;
    }


}
