package com.bjpowernode.crm.workbench.web.controller;

import com.alibaba.druid.util.DruidWebUtils;
import com.bjpowernode.crm.commons.constants.Constants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtil;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.DictionaryValue;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.service.*;
import com.sun.tracing.dtrace.ArgsAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class TransactionController {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request) {
//        List<User> userList = userService.queryAllUsers();
        List<DictionaryValue> sourceList = dictionaryValueService.queryDictionaryValueByTypeCode("source");
        List<DictionaryValue> transactionTypeList = dictionaryValueService.queryDictionaryValueByTypeCode("transactionType");
        List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValueByTypeCode("stage");

//        request.setAttribute("userList", userList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("stageList", stageList);

        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/createTransaction.do")
    public String createTransaction(HttpServletRequest request) {
        List<User> userList = userService.queryAllUsers();
        List<DictionaryValue> sourceList = dictionaryValueService.queryDictionaryValueByTypeCode("source");
        List<DictionaryValue> transactionTypeList = dictionaryValueService.queryDictionaryValueByTypeCode("transactionType");
        List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValueByTypeCode("stage");

        request.setAttribute("userList", userList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("stageList", stageList);

        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/queryCustomerName.do")
    @ResponseBody
    public Object queryCustomerName(String name) {
        return customerService.queryCustomerNameByName(name);
    }

    @RequestMapping("/workbench/transaction/queryActivity.do")
    @ResponseBody
    public Object queryActivity(String name) {
        return activityService.queryActivityByName(name);
    }

    @RequestMapping("/workbench/transaction/queryContacts.do")
    @ResponseBody
    public Object queryContacts(String fullname) {
        return contactsService.queryContactsByName(fullname);
    }

    @RequestMapping("/workbench/transaction/getPossibility.do")
    @ResponseBody
    public Object getPossibility(String stageValue) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        return resourceBundle.getString(stageValue);
    }

    @RequestMapping("/workbench/transaction/saveCreateTransaction.do")
    @ResponseBody
    public Object saveCreateTransaction(@RequestParam Map<String, Object> map, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        map.put(Constants.SESSION_USER, user);
        ReturnObject returnObject = new ReturnObject();

        try {
            transactionService.saveCreateTransaction(map);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙");
        }
        return  returnObject;
    }

    @RequestMapping("/workbench/transaction/detail.do")
    public String detail(String id, HttpServletRequest request) {
        Transaction transaction = transactionService.queryTransactionById(id);

        List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValueByTypeCode("stage");
        request.setAttribute("transaction", transaction);
        request.setAttribute("stageList", stageList);
        return "workbench/transaction/detail";
    }
}
