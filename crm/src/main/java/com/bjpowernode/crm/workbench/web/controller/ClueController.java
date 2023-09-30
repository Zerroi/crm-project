package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.constants.Constants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtil;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-23 15:29
 */

@Controller
public class ClueController {

    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request) {
        List<Clue> clueList = clueService.queryAllClues();
        List<User> userList = userService.queryAllUsers();
        List<DictionaryValue> sourceList = dictionaryValueService.queryDictionaryValueByTypeCode("source");
        List<DictionaryValue> appellationList = dictionaryValueService.queryDictionaryValueByTypeCode("appellation");
        List<DictionaryValue> clueStateList = dictionaryValueService.queryDictionaryValueByTypeCode("clueState");

        request.setAttribute("clueList", clueList);
        request.setAttribute("userList", userList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList", clueStateList);

        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/queryClueByConditionForPagination.do")
    @ResponseBody
    public Object queryClueByConditionForPagination(String fullname, String company, String phone, String source, String owner,
                                                    String mphone, String state, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("mphone", mphone);
        map.put("state", state);
        map.put("beginNo",  (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);

        List<Clue> clueList = clueService.queryClueByConditionForPagination(map);
        int totalRows = clueService.queryTotalRowsByConditionForPagination(map);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("clueList", clueList);
        returnMap.put("totalRows", totalRows);

        return returnMap;
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();

        clue.setId(UUIDUtil.get32UUID());
        clue.setCreateBy(user.getId());
        clue.setEditTime(DateUtils.FormatDate(new Date()));

        try {
            int savedCount = clueService.saveCreateClue(clue);

            if (savedCount > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/toClueDetailPage.do")
    public String toClueDetailPage(String id, HttpServletRequest request) {
        Clue clue = clueService.queryClueDetailInfoById(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkByClueId(id);
        List<Activity> activityList = activityService.queryActivityAssociatedClueByClueId(id);

        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        request.setAttribute("activityList", activityList);

        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/queryNotAssociatedActivity.do")
    @ResponseBody
    public Object queryNotAssociatedActivity(String clueId, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("name", name);

        List<Activity> activityList = activityService.queryNotAssociatedActivity(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveAssociatedActivity.do")
    @ResponseBody
    public Object saveAssociatedActivity(String[] activityId, String clueId) {
        ClueActivityRelation clueActivityRelation = null;
        List<ClueActivityRelation> list = new ArrayList<>();
        ReturnObject returnObject = new ReturnObject();

        for (String id : activityId) {
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.get32UUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(id);
            list.add(clueActivityRelation);
        }

        try {
            int savedCount = clueActivityRelationService.saveAssociatedActivity(list);
            if (savedCount > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList = activityService.queryActivityByIds(activityId);
                returnObject.setRetData(activityList);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }


    @RequestMapping("/workbench/clue/unlinkActivity.do")
    @ResponseBody
    public Object unlinkActivity(ClueActivityRelation relation) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int unlinkedCount = clueActivityRelationService.deleteActivityOfCurrentClue(relation);
            if (unlinkedCount > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String clueId, HttpServletRequest request) {
        Clue clue = clueService.queryClueInfoForConvert(clueId);
        List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValueByTypeCode("stage");

        request.setAttribute("clue", clue);
        request.setAttribute("stageList", stageList);

        return "workbench/clue/convert";
    }

    @RequestMapping("/workbench/clue/queryActivityForTransaction.do")
    @ResponseBody
    public Object queryActivityForTransaction(String clueId, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("name", name);
        return activityService.queryActivityByNameAndclueId(map);
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    @ResponseBody
    public Object convertClue(String clueId, String money, String name, String expectedDate, String stage,
                              String activityId,String isCreateTransaction, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("money", money);
        map.put("name", name);
        map.put("expectedDate", expectedDate);
        map.put("stage", stage);
        map.put("activityId", activityId);
        map.put("isCreateTransaction", isCreateTransaction);
        map.put(Constants.SESSION_USER, user);
//        创建交易

        ReturnObject returnObject = new ReturnObject();
        try {
            clueService.saveConvert(map);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }


}
