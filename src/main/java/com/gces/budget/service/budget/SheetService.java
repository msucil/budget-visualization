package com.gces.budget.service.budget;

import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Created by minamrosh on 11/26/15.
 */

@Service
public class SheetService implements ISheetSerivce{


    private final Logger log = LoggerFactory.getLogger(SheetService.class);

    private HSSFWorkbook xlsSheet;

    /**
     * Read income budget sheet in xls file and return IncomeBudget
     * @param incomeBudgetSheet, MultipartFile
     * @return IncomeBudget
     * @throws IOException
     */
    @Override
    public IncomeBudget getIncomeBudgetFromFile(MultipartFile incomeBudgetSheet) throws IOException{

        xlsSheet = new HSSFWorkbook(incomeBudgetSheet.getInputStream());

        HSSFSheet sheet = xlsSheet.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        IncomeBudget incomeBudget = new IncomeBudget();

//        log.info("reading income sheet");

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

            String incomeBudgetTitle = null;

            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                switch(cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        incomeBudgetTitle = cell.getStringCellValue().trim();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        BigDecimal titleValue = new BigDecimal(cell.getNumericCellValue());
                        incomeBudget = this.setIncomeBudgetData(incomeBudgetTitle,titleValue,incomeBudget);
                        break;
                }
            }
        }

        log.info("reading income budget finished!");

        return incomeBudget;
    }

    /**
     * Read Expense Budget form xls sheet and return Expense Budget
     * @param expenseSheet
     * @return
     * @throws IOException
     */
    @Override
    public ExpenseBudget getExpenseBudgetFromFile(MultipartFile expenseSheet) throws IOException {
        xlsSheet = new HSSFWorkbook(expenseSheet.getInputStream());

        HSSFSheet sheet = xlsSheet.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        ExpenseBudget expenseBudget = new ExpenseBudget();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            String expenseBuegetTitle = null;
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                switch (cell.getCellType()){
                    case  Cell.CELL_TYPE_STRING :
                        expenseBuegetTitle = cell.getStringCellValue().trim();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        BigDecimal expenseAmount = new BigDecimal(cell.getNumericCellValue());
                        this.setExpenseBudgetData(expenseBuegetTitle,expenseAmount,expenseBudget);
                        break;
                }
            }

        }

        return expenseBudget;
    }

    /**
     * sets income amount specified by incomeAmount to the income area specified by incomeBudgetTitle in IncomeBudget
     * incomeBudget and returns it.
     * @param incomeBudgetTitle, String
     * @param incomeAmount, BigDecimal
     * @param incomeBudget, IncomeBudget
     * @return incomeBudget
     */
    private IncomeBudget setIncomeBudgetData(String incomeBudgetTitle, BigDecimal incomeAmount, IncomeBudget incomeBudget){

        switch(incomeBudgetTitle){
            case "Applied and Administration Fee":
                incomeBudget.setAppAdmitionFee(incomeAmount);
                break;
            case "Semester Fee per Year":
                incomeBudget.setSemesterFee(incomeAmount);
                break;
            case "Internet & Library Fee":
                incomeBudget.setInternetLibraryFee(incomeAmount);
                break;
            case "Photocopy Income":
                incomeBudget.setPhotocopyIncome(incomeAmount);
                break;
            case "Student Card":
                incomeBudget.setStudentCard(incomeAmount);
                break;
            case "Telephone Income":
                incomeBudget.setTelephoneIncome(incomeAmount);
                break;
            case "Certificate/Late Fee/ Misc. Income":
                incomeBudget.setMiscellaneousIncome(incomeAmount);
                break;
            case "MEISE Master Degree":
                incomeBudget.setMeiseMasterDegree(incomeAmount);
                break;
        }

        return incomeBudget;
    }


    /**
     * Sets Expense Amount specified by expenseAmount to expenditure specified by expenseBudgetTitle in ExpenseBudget
     * and returns it.
     * @param expenseBudgetTitle, String, title of expenditure
     * @param expenseAmount , BigDecimal, Amount associated with expenditure
     * @param expenseBudget , ExpenseBudget, object of ExpenseBudget
     * @return expenseBudget
     */

    private ExpenseBudget setExpenseBudgetData(String expenseBudgetTitle, BigDecimal expenseAmount, ExpenseBudget expenseBudget){

        switch (expenseBudgetTitle){
            case "Repair & Maintenance" :
                expenseBudget.setRepairMaintenance(expenseAmount);
                break;
            case "Transportation & Fare" :
                expenseBudget.setTransportationFare(expenseAmount);
                break;
            case "Telephone" :
                expenseBudget.setTelephone(expenseAmount);
                break;
            case "Internet Expenses" :
                expenseBudget.setInternetExpense(expenseAmount);
                break;
            case "Stationary/Exam Materials" :
                expenseBudget.setStationaryExamMaterials(expenseAmount);
                break;
            case "Electricity" :
                expenseBudget.setElectricity(expenseAmount);
                break;
            case "Scholarship Award" :
                expenseBudget.setScholarshipAward(expenseAmount);
                break;
            case "Advertisement/Public Relation" :
                expenseBudget.setAdvPubRelation(expenseAmount);
                break;
            case "House Rent" :
                expenseBudget.setHouseRent(expenseAmount);
                break;
            case "Newspaper/Magazine" :
                expenseBudget.setNewsMagazine(expenseAmount);
                break;
            case "Library Books, Ref. Books" :
                expenseBudget.setLibraryBooks(expenseAmount);
                break;
            case "Extra Curricular/Sports Equipments" :
                expenseBudget.setExtraCurricular(expenseAmount);
                break;
            case "Sick Leave & Others" :
                expenseBudget.setSickLeaveOthers(expenseAmount);
                break;
            case "Furniture" :
                expenseBudget.setFurniture(expenseAmount);
                break;
            case  "Depreciation" :
                expenseBudget.setDepreciation(expenseAmount);
                break;
            case "Salary & Allowances" :
                expenseBudget.setSalaryAllowances(expenseAmount);
                break;
            case "Office Equipments & Computers" :
                expenseBudget.setOfficeEqupComputers(expenseAmount);
                break;
            case "Gratuity" :
                expenseBudget.setGratuity(expenseAmount);
                break;
            case "R & D Activity" :
                expenseBudget.setRdActivity(expenseAmount);
                break;
            case "Application Procedure for M. Program" :
                expenseBudget.setAppProcedureMProg(expenseAmount);
                break;
            case "Rebate for Fee" :
                expenseBudget.setRebateForFee(expenseAmount);
                break;
            case "Provident Fund" :
                expenseBudget.setProvidentFund(expenseAmount);
                break;
            case "Students Welfare/IT Mahotsab" :
                expenseBudget.setStudentWalefareMahotsav(expenseAmount);
                break;
            case "OT, Insurance, T.A.D.A" :
                expenseBudget.setOtInsurance(expenseAmount);
                break;
            case "Miscellaneous" :
                expenseBudget.setMiscellaneous(expenseAmount);
                break;
            case "Projected Surplus" :
                expenseBudget.setProjectedSurplus(expenseAmount);
                break;

        }

        return expenseBudget;
    }

}
