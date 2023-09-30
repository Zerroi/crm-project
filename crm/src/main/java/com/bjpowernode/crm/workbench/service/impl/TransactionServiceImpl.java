package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.constants.Constants;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtil;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TransactionMapper;
import com.bjpowernode.crm.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void saveCreateTransaction(Map<String, Object> map) {
        String customerName = (String) map.get("customerName");
        User user = (User) map.get(Constants.SESSION_USER);

        Customer customer = customerMapper.selectCustomerByName(customerName);

        if (customer == null) {
            customer=new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setId(UUIDUtil.get32UUID());
            customer.setCreateTime(DateUtils.FormatDate(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }

        Transaction transaction = new Transaction();
        transaction.setStage((String) map.get("stage"));
        transaction.setOwner((String) map.get("owner"));
        transaction.setNextContactTime((String) map.get("nextContactTime"));
        transaction.setName((String) map.get("name"));
        transaction.setMoney((String) map.get("money"));
        transaction.setId(UUIDUtil.get32UUID());
        transaction.setExpectedDate((String) map.get("expectedDate"));
        transaction.setCustomerId(customer.getId());
        transaction.setCreateTime(DateUtils.FormatDate(new Date()));
        transaction.setCreateBy(user.getId());
        transaction.setContactSummary((String) map.get("contactSummary"));
        transaction.setContactsId((String) map.get("contactsId"));
        transaction.setActivityId((String) map.get("activityId"));
        transaction.setDescription((String) map.get("description"));
        transaction.setSource((String) map.get("source"));
        transaction.setType((String) map.get("type"));
        transactionMapper.insertTran(transaction);
    }

    @Override
    public Transaction queryTransactionById(String id) {
        return transactionMapper.selectTransactionById(id);
    }
}
