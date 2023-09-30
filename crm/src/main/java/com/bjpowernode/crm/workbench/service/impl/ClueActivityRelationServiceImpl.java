package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public int deleteActivityOfCurrentClue(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteActivityOfCurrentClue(relation);
    }

    @Override
    public int saveAssociatedActivity(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.saveAssociatedActivity(list);
    }
}
