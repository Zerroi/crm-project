package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

/**
 * @version: java version 1.8
 * @Author: Zerroi
 * @description:
 * @date: 2023-09-23 15:43
 */
public interface ClueService {
    List<Clue> queryAllClues();
    List<Clue> queryClueByConditionForPagination(Map<String, Object> map);
    int queryTotalRowsByConditionForPagination(Map<String, Object> map);
    int saveCreateClue(Clue clue);
    Clue queryClueDetailInfoById(String id);
    Clue queryClueInfoForConvert(String clueId);
    void saveConvert(Map<String, Object> map);
}
