package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Transaction;

import java.util.Map;

public interface TransactionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Fri Sep 29 17:27:13 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Fri Sep 29 17:27:13 CST 2023
     */
    int insert(Transaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Fri Sep 29 17:27:13 CST 2023
     */
    int insertSelective(Transaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Fri Sep 29 17:27:13 CST 2023
     */
    Transaction selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Fri Sep 29 17:27:13 CST 2023
     */
    int updateByPrimaryKeySelective(Transaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Fri Sep 29 17:27:13 CST 2023
     */
    int updateByPrimaryKey(Transaction record);
    int insertTransactionFromConvert(Transaction transaction);
    void insertTransactionFromCreate(Map<String, Object> map);
    int insertTran(Transaction transaction);
    Transaction selectTransactionById(String id);
}