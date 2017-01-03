package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Dutymodel;

public interface DutymodelMapper {
   //列表
   List<Dutymodel> findDutymodelList1(Dutymodel dutymodel);
   List<Dutymodel> findDutymodelList2();
   //新增
   int addDutymodel(Dutymodel dutymodel);
   //修改
   int updateDutymodel(Dutymodel dutymodel);
   //删除
   int deleteDutymodel(Integer id);
   int usingById(Integer id);//启用该模板
   int disableById(Integer id);//不是该id的模板停用
   
   int selectIdIsUsing();//查询出正在使用的模板的id
   
   Dutymodel selectIsUsing();//查询出正在使用的模板
}