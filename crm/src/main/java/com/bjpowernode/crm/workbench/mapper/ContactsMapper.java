package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Tue Sep 26 22:52:02 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Tue Sep 26 22:52:02 CST 2023
     */
    int insert(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Tue Sep 26 22:52:02 CST 2023
     */
    int insertSelective(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Tue Sep 26 22:52:02 CST 2023
     */
    Contacts selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Tue Sep 26 22:52:02 CST 2023
     */
    int updateByPrimaryKeySelective(Contacts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts
     *
     * @mbggenerated Tue Sep 26 22:52:02 CST 2023
     */
    int updateByPrimaryKey(Contacts record);
    int insertContactInfoFromConvert(Contacts contacts);
    List<Contacts> selectContactsByName(String fullname);
}