package com.gces.budget.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created by minamrosh on 12/9/15.
 */
public class BudgetSheetDTO {

    @NotNull
    private String fiscalYear;

    @NotNull
    private MultipartFile budgetSheet;


    private String sheetId;

    /**
     * true income;
     * false expense;
     */
    @NotNull
    private boolean sheetType;

    public void setFiscalYear(String fiscalYear){
        this.fiscalYear = fiscalYear;
    }

    public String getFiscalYear(){
        return this.fiscalYear;
    }

    public void setBudgetSheet(MultipartFile sheet){
        this.budgetSheet = sheet;
    }

    public void setSheetType(boolean sheetType){
        this.sheetType = sheetType;
    }

    public boolean getSheetType(){
        return this.sheetType;
    }

    public MultipartFile getBudgetSheet(){
        return  this.budgetSheet;
    }

    public void setSheetId(String sheetId){
        this.sheetId = sheetId;
    }

    public String getSheetId(){
        return this.sheetId;
    }

    public String toString(){
        return "Budget Sheet :\n"+
                "{fiscalYear : " + this.fiscalYear+ ",\n"+
                "budgetSheet : " + this.budgetSheet.getName()+"\n"+
                "Sheet Type : " + this.sheetType + ",\n" +
                "}";
    }

}
