package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    int deleteActivityOfCurrentClue(ClueActivityRelation relation);
    int saveAssociatedActivity(List<ClueActivityRelation> list);

}
