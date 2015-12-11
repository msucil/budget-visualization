package com.gces.budget.service.budget;

import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by minamrosh on 12/9/15.
 */
public interface ISheetSerivce {
    public IncomeBudget getIncomeBudgetFromFile(MultipartFile incomeSheet) throws IOException;

    public ExpenseBudget getExpenseBudgetFromFile(MultipartFile expenseSheet) throws IOException;
}
