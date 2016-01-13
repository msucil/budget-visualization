package com.gces.budget.service.budget.analysis;

import com.gces.budget.domain.dto.BudgetDTO;
import com.gces.budget.repository.IncomeBudgetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by minamrosh on 12/15/15.
 */

@Service
public class BudgetAnalysisService {

    private final Logger log = LoggerFactory.getLogger(BudgetAnalysisService.class);

    private double intercept, slope;

    private double budgetSum = 0.0, budgetYrIntervalSum = 0.0;

    private double yearIntervalSum = 0.0, yearIntervalSqrSum = 0.0;

    int yearInterval[];

    private List<BudgetDTO> budgetDTOs;

    private IncomeBudgetRepository incomeBudgetRepository;

    public BudgetAnalysisService(){}

    public BudgetAnalysisService(List<BudgetDTO> budgetDTOs){
        this.budgetDTOs = budgetDTOs;
        calculateLeastSquare();
    }

    private void calculateLeastSquare(){
        calculateYearInterval();
        calculateYearIntervalSum();
        calculateBudgetSum();
        calculateBudgetYrIntervalSum();
        calculateYearIntervalSqrSum();
        calculateSlope();
        calculateIntercept();
    }

    private void calculateYearInterval(){

        int size = budgetDTOs.size();
        log.info(String.valueOf(size));
        yearInterval = new int[size];
        for(int i = 1; i <= size; i++){
            this.yearInterval[i-1] = i;
        }
    }

    private void calculateYearIntervalSum(){
        for(int i = 0; i < yearInterval.length; i++){
            yearIntervalSum += yearInterval[i];
        }
    }

    private void calculateYearIntervalSqrSum(){
        for(int i = 0; i < yearInterval.length; i++){
            yearIntervalSqrSum += Math.pow(yearInterval[i],2);
        }
    }

    private void calculateBudgetSum(){
        for(BudgetDTO budgetDTO : budgetDTOs){
            budgetSum += budgetDTO.getTotalAmount();
            log.info("Budget Sum " + budgetSum);
        }
    }

    private void calculateBudgetYrIntervalSum(){
        int i = 0;
        for(BudgetDTO budgetDTO : budgetDTOs){
            budgetYrIntervalSum += budgetDTO.getTotalAmount() * yearInterval[i];
//                    budgetYrIntervalSum.add(budgetDTO.getTotalAmount().multiply(BigDecimal.valueOf(yearInterval[i])));
            i++;
            log.info("Budget Year Interval Sum " + budgetYrIntervalSum);
        }
    }

    private void calculateSlope(){
        double up = (budgetYrIntervalSum * yearInterval.length) - (budgetSum * yearIntervalSum);
//                budgetYrIntervalSum.multiply(BigDecimal.valueOf(yearInterval.length))
//                .subtract(budgetSum.multiply(BigDecimal.valueOf(yearIntervalSum)));

        log.info("up" + up);

        double down = (yearInterval.length * yearIntervalSqrSum) - (yearIntervalSum * yearIntervalSum);
//                BigDecimal.valueOf(yearInterval.length).multiply(BigDecimal.valueOf(yearIntervalSqrSum))
//                .subtract(BigDecimal.valueOf(yearIntervalSum * yearIntervalSum));
        log.info("down" + down);
        slope = up / down ;
    }

    private void calculateIntercept(){


        double up = budgetSum / yearInterval.length;
//                budgetSum.divide(BigDecimal.valueOf(yearInterval.length));

        double down = slope * (yearIntervalSum/yearInterval.length);
//                slope.multiply(BigDecimal.valueOf(yearIntervalSum).
//                divide(BigDecimal.valueOf(yearInterval.length)));

        intercept = up - down;
    }

    public int[] getYearInterval(){
        return yearInterval;
    }

    public double getYearIntervalSum(){
        return yearIntervalSum;
    }

    public double getYearIntervalSqrSum(){
        return yearIntervalSqrSum;
    }

    public double getBudgetSum(){
        return budgetSum;
    }

    public double getBudgetYrIntervalSum(){
        return budgetYrIntervalSum;
    }

    public double getIntercept(){
        return intercept;
    }

    public double getSlope(){
        return slope;
    }

}
