package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.ComputerManage;

public interface ComputerManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComputerManage record);

    int insertSelective(ComputerManage record);

    ComputerManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComputerManage record);

    int updateByPrimaryKey(ComputerManage record);
    
    List<ComputerManage> computermanage(ComputerManage record);
}