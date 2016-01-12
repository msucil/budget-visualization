package com.gces.budget.service.budget.analysis;

import com.gces.budget.repository.IncomeBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by minamrosh on 12/15/15.
 */

@Service
public class BudgetAnalysisService {

    private IncomeBudgetRepository incomeBudgetRepository;

    @Autowired
    public void setIncomeBudgetRepository(IncomeBudgetRepository incomeRepo){
        this.incomeBudgetRepository = incomeRepo;
    }


}
