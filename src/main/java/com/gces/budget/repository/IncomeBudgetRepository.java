package com.gces.budget.repository;

import com.gces.budget.domain.entity.IncomeBudget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by minamrosh on 12/9/15.
 */
@Repository
public interface IncomeBudgetRepository extends MongoRepository<IncomeBudget, String> {

    public IncomeBudget findOneByFiscalYearAndUserId(String fiscalYear,String userId);

    public List<IncomeBudget> findAllByUserId(String userId);

    @Override
    public void delete(IncomeBudget incomeBudget);
}
