package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.constants.Constants;
import com.bjpowernode.crm.commons.utils.UUIDUtil;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-23 15:44
 */

@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionRemarkMapper transactionRemarkMapper;

    @Override
    public List<Clue> queryAllClues() {
        return clueMapper.selectAllClues();
    }

    @Override
    public List<Clue> queryClueByConditionForPagination(Map<String, Object> map) {
        return clueMapper.selectClueByConditionForPagination(map);
    }

    @Override
    public int queryTotalRowsByConditionForPagination(Map<String, Object> map) {
        return clueMapper.selectTotalRowsByConditionForPagination(map);
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertSaveCreateClue(clue);
    }

    @Override
    public Clue queryClueDetailInfoById(String id) {
        return clueMapper.selectClueDetailInfoById(id);
    }

    @Override
    public Clue queryClueInfoForConvert(String id) {
        return clueMapper.selectClueInfoForConvert(id);
    }

    @Override
    public void saveConvert(Map<String, Object> map) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(Constants.SESSION_USER);

        Clue clue = clueMapper.selectClueInfoForConvert(clueId);

        Customer customer = new Customer();
        customer.setId(UUIDUtil.get32UUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getFullname());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(clue.getCreateBy());
        customer.setCreateTime(clue.getCreateTime());
        customer.setEditBy(clue.getEditBy());
        customer.setEditTime(clue.getEditTime());
        customer.setContactSummary(clue.getContactSummary());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());
        customerMapper.insertCustomerInfoFromConvert(customer);

        Contacts contacts = new Contacts();

        contacts.setId(UUIDUtil.get32UUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(clue.getCreateBy());
        contacts.setCreateTime(clue.getCreateTime());
        contacts.setEmail(clue.getEmail());
        contacts.setEditTime(clue.getEditTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        contactsMapper.insertContactInfoFromConvert(contacts);

        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);

        if (clueRemarkList != null && !clueRemarkList.isEmpty()) {
            CustomerRemark customerRemark = null;
            List<CustomerRemark> customerRemarkList = new ArrayList<>();
            ContactsRemark contactsRemark = null;
            List<ContactsRemark> contactsRemarkList = new ArrayList<>();

            for (ClueRemark clueRemark : clueRemarkList) {
                customerRemark = new CustomerRemark();

                customerRemark.setId(UUIDUtil.get32UUID());
                customerRemark.setNoteContent(clueRemark.getNoteContent());
                customerRemark.setCreateBy(clueRemark.getCreateBy());
                customerRemark.setCreateTime(clueRemark.getCreateTime());
                customerRemark.setEditBy(clueRemark.getEditBy());
                customerRemark.setEditTime(clueRemark.getEditTime());
                customerRemark.setEditFlag(clueRemark.getEditFlag());
                customerRemark.setCustomerId(customer.getId());
                customerRemarkList.add(customerRemark);

                contactsRemark = new ContactsRemark();
                contactsRemark.setId(UUIDUtil.get32UUID());
                contactsRemark.setNoteContent(clueRemark.getNoteContent());
                contactsRemark.setCreateBy(clueRemark.getCreateBy());
                contactsRemark.setCreateTime(clueRemark.getCreateTime());
                contactsRemark.setEditBy(clueRemark.getEditBy());
                contactsRemark.setEditTime(clueRemark.getEditTime());
                contactsRemark.setEditFlag(clueRemark.getEditFlag());
                contactsRemark.setContactsId(contacts.getId());
                contactsRemarkList.add(contactsRemark);
            }
            customerRemarkMapper.insertCustomerRemarkFromConvert(customerRemarkList);

            contactsRemarkMapper.insertContactsRemarkFromConvert(contactsRemarkList);
        }


        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationForConvert(clueId);
        

        if (clueActivityRelationList != null && !clueActivityRelationList.isEmpty()) {
            List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
            ContactsActivityRelation contactsActivityRelation = null;
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
                contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setId(UUIDUtil.get32UUID());
                contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
                contactsActivityRelation.setContactsId(contacts.getId());
                contactsActivityRelationList.add(contactsActivityRelation);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationFromConvert(contactsActivityRelationList);
        }
        

        String isCreateTransaction = (String) map.get("isCreateTransaction");
        if ("true".equals(isCreateTransaction)) {
            String money = (String) map.get("money");
            String name = (String) map.get("name");
            String expectedDate = (String) map.get("expectedDate");
            String stage = (String) map.get("stage");
            String activityId = (String) map.get("activityId");
            Transaction transaction = new Transaction();
            transaction.setId(UUIDUtil.get32UUID());
            transaction.setMoney(money);
            transaction.setName(name);
            transaction.setExpectedDate(expectedDate);
            transaction.setStage(stage);
            transaction.setActivityId(activityId);

            transactionMapper.insertTransactionFromConvert(transaction);

            if (clueRemarkList != null && !clueRemarkList.isEmpty()) {
                List<TransactionRemark> transactionRemarkList = new ArrayList<>();
                TransactionRemark transactionRemark = null;
                for (ClueRemark clueRemark : clueRemarkList) {
                    transactionRemark = new TransactionRemark();
                    transactionRemark.setId(UUIDUtil.get32UUID());
                    transactionRemark.setNoteContent(clueRemark.getNoteContent());
                    transactionRemark.setCreateBy(clueRemark.getCreateBy());
                    transactionRemark.setCreateTime(clueRemark.getCreateTime());
                    transactionRemark.setEditBy(clueRemark.getEditBy());
                    transactionRemark.setEditTime(clueRemark.getEditTime());
                    transactionRemark.setEditFlag(clueRemark.getEditFlag());
                    transactionRemark.setTranId(transaction.getId());
                    transactionRemarkList.add(transactionRemark);
                }
                transactionRemarkMapper.insertTransactionRemarkFromConvert(transactionRemarkList);
            }
        }

        clueMapper.deleteClueForConvert(clueId);
        clueActivityRelationMapper.deleteClueActivityRelationForConvert(clueId);
        clueMapper.deleteClueForConvert(clueId);
    }


}
