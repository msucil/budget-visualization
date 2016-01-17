package com.gces.budget.repository;

import com.gces.budget.domain.entity.ExpenseBudget;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by minamrosh on 12/11/15.
 */
public interface ExpenseBudgetRepository extends MongoRepository<ExpenseBudget, String> {
    public ExpenseBudget findOneByFiscalYearAndUserId(String fiscalYear,String userId);

    public List<ExpenseBudget> findAllByFiscalYear(String fiscalYear);

    public List<ExpenseBudget> findAllByUserIdOrderByFiscalYearDesc(String userId);

    public ExpenseBudget findOneByFiscalYear(String fiscalYear);

    public ExpenseBudget findOneByUserIdOrderByFiscalYearDesc(String userId);

    @Override
    public void delete(ExpenseBudget expenseBudget);
}
