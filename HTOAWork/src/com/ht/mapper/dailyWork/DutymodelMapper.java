package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Dutymodel;

public interface DutymodelMapper {
   //�б�
   List<Dutymodel> findDutymodelList1(Dutymodel dutymodel);
   List<Dutymodel> findDutymodelList2();
   //����
   int addDutymodel(Dutymodel dutymodel);
   //�޸�
   int updateDutymodel(Dutymodel dutymodel);
   //ɾ��
   int deleteDutymodel(Integer id);
   int usingById(Integer id);//���ø�ģ��
   int disableById(Integer id);//���Ǹ�id��ģ��ͣ��
   
   int selectIdIsUsing();//��ѯ������ʹ�õ�ģ���id
   
   Dutymodel selectIsUsing();//��ѯ������ʹ�õ�ģ��
}