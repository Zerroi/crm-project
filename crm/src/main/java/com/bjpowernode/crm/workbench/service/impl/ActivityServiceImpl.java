package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-15 19:58
 */

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public int deleteActivityById(String[] ids) {
        return activityMapper.deleteActivityById(ids);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int updateActivityById(Activity activity) {
        return activityMapper.updateByPrimaryKey(activity);
    }

    @Override
    public List<Activity> queryAllActivities() {
        return activityMapper.selectAllActivities();
    }

    @Override
    public int saveCreateActivityByList(List<Activity> activityList) {
        return activityMapper.insertActivityByList(activityList);
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> queryActivityForSelectedExport(String[] id) {
        return activityMapper.selectActivityForSelectedExport(id);
    }

    @Override
    public List<Activity> queryActivityAssociatedClueByClueId(String clueId) {
        return activityMapper.selectActivityAssociatedClueByClueId(clueId);
    }

    @Override
    public List<Activity> queryActivityByIds(String[] id) {
        return activityMapper.selectActivityByIds(id);
    }

    @Override
    public List<Activity> queryNotAssociatedActivity(Map<String, Object> map) {
        return activityMapper.selectNotAssociatedActivity(map);
    }

    @Override
    public List<Activity> queryActivityByNameAndclueId(Map<String, Object> map) {
        return activityMapper.selectActivityByNameAndclueId(map);
    }

    @Override
    public List<Activity> queryActivityByName(String name) {
        return activityMapper.selectActivityByName(name);
    }

    /*@Override
    public List<Activity> queryActivityByNameForCreateTransaction(String name) {
        return activityMapper.selectActivityByName(name);
    }*/
}
