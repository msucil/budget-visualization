package com.gces.budget.service.budgetanalysis;

import com.gces.budget.repository.IncomeBudgetRepository;

/**
 * Created by minamrosh on 12/15/15.
 */
public class BudgetAnalysisService {

    private IncomeBudgetRepository incomeBudgetRepository;

    public void setIncomeBudgetRepository(IncomeBudgetRepository incomeRepo){
        this.incomeBudgetRepository = incomeRepo;
    }


}
