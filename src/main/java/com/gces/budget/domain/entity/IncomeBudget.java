package com.gces.budget.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by minamrosh on 12/9/15.
 */
@Document(collection = "incomeBudget")
public class IncomeBudget {


    @Id
    private String id;

    @NotNull(message = "Fiscal Year is required")
    @Indexed(unique = true)
    private String fiscalYear;

    @NotNull
    private BigDecimal appAdmitionFee;

    @NotNull
    private BigDecimal semesterFee;

    @NotNull
    private BigDecimal internetLibraryFee;

    @NotNull
    private BigDecimal photocopyIncome;

    @NotNull
    private BigDecimal telephoneIncome;

    @NotNull
    private BigDecimal studentCard;

    @NotNull
    private BigDecimal miscellaneousIncome;

    @NotNull
    private String userId;

    public IncomeBudget(){}

    private BigDecimal meiseMasterDegree;

    public String getId(){
        return this.id;
    }

    public void setFiscalYear(String fiscalYear){
        this.fiscalYear = fiscalYear;
    }

    public String getFiscalYear(){
        return this.fiscalYear;
    }

    public void setAppAdmitionFee(BigDecimal appAdmitionFee){
        this.appAdmitionFee = appAdmitionFee;
    }

    public BigDecimal getAppAdmitionFee(){
        return this.appAdmitionFee;
    }

    public void setSemesterFee(BigDecimal semesterFee){
        this.semesterFee = semesterFee;
    }

    public BigDecimal getSemesterFee(){
        return this.semesterFee;
    }

    public void setInternetLibraryFee(BigDecimal internetLibraryFee){
        this.internetLibraryFee = internetLibraryFee;
    }

    public BigDecimal getInternetLibraryFee(){
        return  this.internetLibraryFee;
    }

    public void setPhotocopyIncome(BigDecimal photocopyIncome){
        this.photocopyIncome = photocopyIncome;
    }

    public BigDecimal getPhotocopyIncome(){
        return this.photocopyIncome;
    }

    public void setTelephoneIncome(BigDecimal telephoneIncome){
        this.telephoneIncome = telephoneIncome;
    }

    public BigDecimal getTelephoneIncome(){
        return this.telephoneIncome;
    }

    public void setStudentCard(BigDecimal studentCard){
        this.studentCard = studentCard;
    }

    public BigDecimal getStudentCard(){
        return this.studentCard;
    }

    public void setMiscellaneousIncome(BigDecimal miscellaneousIncome){
        this.miscellaneousIncome = miscellaneousIncome;
    }

    public BigDecimal getMiscellaneousIncome(){
        return this.miscellaneousIncome;
    }

    public void setMeiseMasterDegree(BigDecimal meiseMasterDegree){
        this.meiseMasterDegree = meiseMasterDegree;
    }

    public BigDecimal getMeiseMasterDegree(){
        return this.meiseMasterDegree;
    }

    public void setUserId(String userid){
        this.userId = userid;
    }

    public String getUserId(){
        return  this.userId;
    }

    @Override
    public String toString(){
        return "Income Bugdget for FY "+this.fiscalYear+
                "{\n"+
                "Id : " + this.id +",\n" +
                "Applied and Administration Fee : " + this.appAdmitionFee + ",\n" +
                "Semester Fee per Year : " + this.semesterFee + ", \n" +
                "Internet & Library Fee : " + this.internetLibraryFee + ", \n" +
                "Photocopy Income : " + this.photocopyIncome + ", \n" +
                "Student Card : " + this.studentCard + ", \n" +
                "Telephone Income : " + this.telephoneIncome + ", \n" +
                "Certificate/Late Fee/Misc. Income : " + this.miscellaneousIncome + ", \n" +
                "MEISE Master Degree : " + this.meiseMasterDegree + "\n " +
                "userId : " +this.userId +
                "\n }";
    }
}
