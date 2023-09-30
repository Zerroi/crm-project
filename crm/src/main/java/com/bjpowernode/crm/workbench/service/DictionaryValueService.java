package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.DictionaryValue;

import java.util.List;

public interface DictionaryValueService {
    List<DictionaryValue> queryDictionaryValueByTypeCode(String typeCode);
}
