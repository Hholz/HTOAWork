package com.ht.mapper.market;

import java.util.List;

import com.ht.popj.market.PredestinationStudent;

public interface PredestinationStudentMapper {
    //�б�
	List<PredestinationStudent> findPrestudentList1(PredestinationStudent prestudent);
	List<PredestinationStudent> findPrestudentList2();
	//����
	int addPrestudent(PredestinationStudent prestudent);
	//�޸�
	int updatePrestudent(PredestinationStudent prestudent);
	//ɾ��
	int deletePrestudnet(Integer id);
	//ͨ��id������Ϣ
	PredestinationStudent selectPrestudentById(Integer id);
}