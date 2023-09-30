package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-15 19:59
 */
public interface ActivityService {

    int saveCreateActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String, Object> map);
    int queryCountOfActivityByCondition(Map<String, Object> map);
    int deleteActivityById(String[] ids);
    Activity queryActivityById(String id);

    int updateActivityById(Activity activity);
    List<Activity> queryAllActivities();
    int saveCreateActivityByList(List<Activity> activityList);
    Activity queryActivityForDetailById(String id);
    List<Activity> queryActivityForSelectedExport(String[] id);
    List<Activity> queryActivityAssociatedClueByClueId(String clueId);
    List<Activity> queryActivityByIds(String[] id);
    List<Activity> queryNotAssociatedActivity(Map<String, Object> map);
    List<Activity> queryActivityByNameAndclueId(Map<String, Object> map);
    List<Activity> queryActivityByName(String name);
}
