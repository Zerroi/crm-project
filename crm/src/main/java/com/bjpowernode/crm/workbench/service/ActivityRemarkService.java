package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-22 20:06
 */
public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String id);
    int saveCreateActivityRemark(ActivityRemark activityRemark);
    int deleteActivityRemark(String id);
    int modifyActivityRemarkById(ActivityRemark activityRemark);
}
