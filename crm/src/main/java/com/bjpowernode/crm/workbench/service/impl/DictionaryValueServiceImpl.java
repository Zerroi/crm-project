package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.DictionaryValue;
import com.bjpowernode.crm.workbench.mapper.DictionaryValueMapper;
import com.bjpowernode.crm.workbench.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dictionaryValueService")
public class DictionaryValueServiceImpl implements DictionaryValueService {

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Override
    public List<DictionaryValue> queryDictionaryValueByTypeCode(String typeCode) {
        return dictionaryValueMapper.selectDictionaryValueByTypeCode(typeCode);
    }
}
