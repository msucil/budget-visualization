package com.gces.budget.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by minamrosh on 12/9/15.
 */
@Document(collection = "expenseBudget")
public class ExpenseBudget {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String fiscalYear;

    @NotNull
    private String userId;

    //Repair & Maintenance
    @NotNull
    private BigDecimal repairMaintenance;

    //Transportation Fare
    @NotNull
    private BigDecimal transportationFare;

    //Telephone
    @NotNull
    private BigDecimal telephone;

    //Internal Expenses
    @NotNull
    private BigDecimal internetExpense;

    //Stationary/Exam Materials
    @NotNull
    private BigDecimal stationaryExamMaterials;

    //Electricity
    @NotNull
    private BigDecimal electricity;

    //Scholarship Award
    @NotNull
    private BigDecimal scholarshipAward;

    //Advertisement/Public Relation
    @NotNull
    private BigDecimal advPubRelation;

    //House Rent
    @NotNull
    private BigDecimal houseRent;

//    Newspaper/Magazine
    @NotNull
    private BigDecimal newsMagazine;

    //Library Books, Ref. Books
    @NotNull
    private BigDecimal libraryBooks;

    //Extra Curricular/Sports Equipment
    @NotNull
    private BigDecimal extraCurricular;

    //Sick Leave & Others
    @NotNull
    private BigDecimal sickLeaveOthers;

    //Furniture
    @NotNull
    private BigDecimal furniture;

    //Depreciation
    @NotNull
    private BigDecimal depreciation;

    //Salary & Allowances
    @NotNull
    private BigDecimal salaryAllowances;

    // Office Equipment/ Computers
    @NotNull
    private BigDecimal officeEqupComputers;

    //Gratuity
    @NotNull
    private BigDecimal gratuity;

    //R & D Activity
    @NotNull
    private BigDecimal rdActivity;

    //Application Procedure for M. Program
    @NotNull
    private BigDecimal appProcedureMProg;

    //Rebate for Fee
    @NotNull
    private BigDecimal rebateForFee;

    //Provident Fund;
    @NotNull
    private BigDecimal providentFund;

    //Student Walefare / IT Mahotsab
    @NotNull
    private BigDecimal studentWalefareMahotsav;

    //OT, Insurance, T.A.D.A
    @NotNull
    private BigDecimal otInsurance;

    //Miscellaneous
    @NotNull
    private BigDecimal miscellaneous;

    //Project Surplus
    @NotNull
    private BigDecimal projectedSurplus;

    public String getId(){
        return this.id;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setFiscalYear(String fiscalYear){
        this.fiscalYear = fiscalYear;
    }

    public String getFiscalYear(){
        return this.fiscalYear;
    }

    public void setRepairMaintenance(BigDecimal repairMaintenance){
        this.repairMaintenance = repairMaintenance;
    }

    public BigDecimal getRepairMaintenance(){
        return this.repairMaintenance;
    }

    public void setTransportationFare(BigDecimal transportationFare){
        this.transportationFare = transportationFare;
    }

    public BigDecimal getTransportationFare(){
        return this.transportationFare;
    }

    public void setTelephone(BigDecimal telephone){
        this.telephone = telephone;
    }

    public BigDecimal getTelephone(){
        return this.telephone;
    }

    public void setInternetExpense(BigDecimal internetExpense){
        this.internetExpense = internetExpense;
    }

    public BigDecimal getInternetExpense(){
        return this.internetExpense;
    }

    public void setStationaryExamMaterials(BigDecimal stationaryExamMaterials){
        this.stationaryExamMaterials = stationaryExamMaterials;
    }

    public BigDecimal getStationaryExamMaterials(){
        return this.stationaryExamMaterials;
    }

    public void setElectricity(BigDecimal electricity){
        this.electricity = electricity;
    }

    public BigDecimal getElectricity(){
        return this.electricity;
    }

    public void setScholarshipAward(BigDecimal scholarshipAward){
        this.scholarshipAward = scholarshipAward;
    }

    public BigDecimal getScholarshipAward(){
        return  this.scholarshipAward;
    }

    public void setAdvPubRelation(BigDecimal advPubRelation){
        this.advPubRelation = advPubRelation;
    }

    public BigDecimal getAdvPubRelation(){
        return this.advPubRelation;
    }

    public void setHouseRent(BigDecimal houseRent){
        this.houseRent = houseRent;
    }

    public BigDecimal getHouseRent(){
        return this.houseRent;
    }

    public void setNewsMagazine(BigDecimal newsMagazine){
        this.newsMagazine = newsMagazine;
    }

    public BigDecimal getNewsMagazine(){
        return this.newsMagazine;
    }

    public void setLibraryBooks(BigDecimal libraryBooks){
        this.libraryBooks = libraryBooks;
    }

    public BigDecimal getLibraryBooks(){
        return this.libraryBooks;
    }

    public void setExtraCurricular(BigDecimal extraCurricular){
        this.extraCurricular = extraCurricular;
    }

    public BigDecimal getExtraCurricular(){
        return this.extraCurricular;
    }

    public void setSickLeaveOthers(BigDecimal sickLeaveOthers){
        this.sickLeaveOthers = sickLeaveOthers;
    }

    public BigDecimal getSickLeaveOthers(){
        return this.sickLeaveOthers;
    }

    public void setFurniture(BigDecimal furniture){
        this.furniture = furniture;
    }

    public BigDecimal getFurniture(){
        return this.furniture;
    }

    public void setDepreciation(BigDecimal depreciation){
        this.depreciation = depreciation;
    }

    public BigDecimal getDepreciation(){
        return this.depreciation;
    }

    public void setSalaryAllowances(BigDecimal salaryAllowances){
        this.salaryAllowances = salaryAllowances;
    }

    public BigDecimal getSalaryAllowances(){
        return  this.salaryAllowances;
    }

    public void setOfficeEqupComputers(BigDecimal officeEqupComputers){
        this.officeEqupComputers = officeEqupComputers;
    }

    public BigDecimal getOfficeEqupComputers(){
        return  this.officeEqupComputers;
    }

    public void setGratuity(BigDecimal gratuity){
        this.gratuity = gratuity;
    }

    public BigDecimal getGratuity(){
        return this.gratuity;
    }

    public void setRdActivity(BigDecimal rdActivity){
        this.rdActivity = rdActivity;
    }

    public BigDecimal getRdActivity(){
        return this.rdActivity;
    }

    public void setAppProcedureMProg(BigDecimal appProcedureMProg){
        this.appProcedureMProg = appProcedureMProg;
    }

    public BigDecimal getAppProcedureMProg(){
        return this.appProcedureMProg;
    }

    public void setRebateForFee(BigDecimal rebateForFee){
        this.rebateForFee = rebateForFee;
    }

    public BigDecimal getRebateForFee(){
        return  this.rebateForFee;
    }

    public void setProvidentFund(BigDecimal providentFund){
        this.providentFund = providentFund;
    }

    public BigDecimal getProvidentFund(){
        return this.providentFund;
    }

    public void setStudentWalefareMahotsav(BigDecimal studentWalefareMahotsav){
        this.studentWalefareMahotsav = studentWalefareMahotsav;
    }

    public BigDecimal getStudentWalefareMahotsav(){
        return this.studentWalefareMahotsav;
    }

    public void setOtInsurance(BigDecimal otInsurance){
        this.otInsurance = otInsurance;
    }

    public BigDecimal getOtInsurance(){
        return  otInsurance;
    }

    public void setMiscellaneous(BigDecimal miscellaneous){
        this.miscellaneous = miscellaneous;
    }

    public BigDecimal getMiscellaneous(){
        return  this.miscellaneous;
    }

    public void setProjectedSurplus(BigDecimal projectedSurplus){
        this.projectedSurplus = projectedSurplus;
    }

    public BigDecimal getProjectedSurplus(){
        return  this.projectedSurplus;
    }

    @Override
    public String toString(){
        return "Expense Budget for FY " + this.fiscalYear + "\n{ \n" +
                "Repair & Maintenance : " + this.repairMaintenance + ", \n" +
                "Transportation & Fare : " + this.transportationFare + ", \n" +
                "Telephone : " + this.telephone + ", \n" +
                "Internet Expense : " + this.internetExpense + ", \n" +
                "Stationary/Exam Materials : " + this.stationaryExamMaterials + ",\n" +
                "Electricity : " + this.electricity + ", \n" +
                "Scholarship Award : " + this.scholarshipAward + ", \n" +
                "Advertisement/Public Relation : " + this.advPubRelation + ", \n" +
                "House Rent : " + this.houseRent + ", \n" +
                "Newspaper/Magazine : " + this.newsMagazine + ", \n" +
                "Library Books, Ref. Books : " + this.libraryBooks + ",\n" +
                "Extra Curricular/Sports Equipments : " + this.extraCurricular + ", \n" +
                "Sick Leave & Others : " + this.sickLeaveOthers + ", \n" +
                "Furniture : " + this.furniture + ",\n" +
                "Depreciaton : " + this.depreciation + ",\n" +
                "Sallary & Allowance : " + this.salaryAllowances + ",\n" +
                "Office Equipments & Computers : " + this.officeEqupComputers + ",\n" +
                "Gratuity : " + this.gratuity +",\n" +
                "R & D Activity : " + this.rdActivity + ",\n" +
                "Application Procedure for M. Program : " + this.appProcedureMProg + ", \n" +
                "Rebate for Fee : " + this.rebateForFee + ",\n" +
                "Provident Fund : " + this.providentFund + ",\n" +
                "Student Walefare/IT Mohatsab : " + this.studentWalefareMahotsav + ",\n" +
                "OT, Insurance, T.A.D.A : " + this.otInsurance + ",\n" +
                "Projected Surplus : " + this.projectedSurplus + "\n}";

    }
}