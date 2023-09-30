package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Transaction;

import java.util.Map;

public interface TransactionService {

    void saveCreateTransaction(Map<String, Object> map);
    Transaction queryTransactionById(String id);
}
