package com.ht.mapper.market;

import java.util.List;

import com.ht.popj.market.PredestinationStudent;

public interface PredestinationStudentMapper {
    //列表
	List<PredestinationStudent> findPrestudentList1(PredestinationStudent prestudent);
	List<PredestinationStudent> findPrestudentList2();
	//新增
	int addPrestudent(PredestinationStudent prestudent);
	//修改
	int updatePrestudent(PredestinationStudent prestudent);
	//删除
	int deletePrestudnet(Integer id);
	//通过id查找信息
	PredestinationStudent selectPrestudentById(Integer id);
}