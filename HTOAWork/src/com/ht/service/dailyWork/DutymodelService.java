package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Dutymodel;

public interface DutymodelService {
   //�б�
   List<Dutymodel> findDutymodelList1(Dutymodel dutymodel);
   List<Dutymodel> findDutymodelList2();
   //����
   int addDutymodel(Dutymodel dutymodel);
   //�޸�
   int updateDutymodel(Dutymodel dutymodel);
   //ɾ��
   int deleteDutymodel(Integer id);
   
   int usingById(Integer id);
   
   int selectIdIsUsing();//��ѯ������ʹ�õ�ģ���id
   
   Dutymodel selectIsUsing();//��ѯ������ʹ�õ�ģ��
}
