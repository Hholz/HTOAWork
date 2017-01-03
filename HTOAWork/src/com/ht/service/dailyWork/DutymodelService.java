package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Dutymodel;

public interface DutymodelService {
   //列表
   List<Dutymodel> findDutymodelList1(Dutymodel dutymodel);
   List<Dutymodel> findDutymodelList2();
   //新增
   int addDutymodel(Dutymodel dutymodel);
   //修改
   int updateDutymodel(Dutymodel dutymodel);
   //删除
   int deleteDutymodel(Integer id);
   
   int usingById(Integer id);
   
   int selectIdIsUsing();//查询出正在使用的模板的id
   
   Dutymodel selectIsUsing();//查询出正在使用的模板
}
