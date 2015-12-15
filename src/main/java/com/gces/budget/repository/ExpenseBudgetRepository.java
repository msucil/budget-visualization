package com.gces.budget.repository;

import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by minamrosh on 12/11/15.
 */
public interface ExpenseBudgetRepository extends MongoRepository<ExpenseBudget, String> {
    public IncomeBudget findOneByFiscalYearAndUserId(String fiscalYear,String userId);

    public List<IncomeBudget> findAllByFiscalYear(String fiscalYear);

    public List<IncomeBudget> findAllByUserId(String userId);

    public ExpenseBudget findOneByFiscalYear(String fiscalYear);

    @Override
    public void delete(ExpenseBudget expenseBudget);
}
