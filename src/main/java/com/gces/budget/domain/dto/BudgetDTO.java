package com.gces.budget.domain.dto;

/**
 * Created by minamrosh on 1/12/16.
 */
public class BudgetDTO {
    private String fiscalYear;

    double totalAmount;

    public BudgetDTO(){}

    public BudgetDTO(String fiscalYear, double totalAmount){
        this.fiscalYear = fiscalYear;
        this.totalAmount = totalAmount;
    }

    public void setFiscalYear(String fiscalYear){
        this.fiscalYear = fiscalYear;
    }

    public String getFiscalYear(){
        return this.fiscalYear;
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount(){
        return  this.totalAmount;
    }

    @Override
    public String toString(){
        return "BudgetDTO {\n" +
                "\t Fiscal Year : " + this.fiscalYear + "\n" +
                "\t Total Amount : " + this.totalAmount + "\n" +
                "}";
    }
}
