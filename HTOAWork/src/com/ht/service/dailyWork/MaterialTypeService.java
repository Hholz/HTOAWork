package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.MaterialType;
public interface MaterialTypeService {
	
	//����
	public int addMaterialType(MaterialType materialtype);
	//�޸�
	public void updateMaterialType(MaterialType materialtype);
	//ɾ��
	public void deleteMaterialType(int id);
	//��ѯList
	public List<MaterialType> findListMaterialType(MaterialType materialtype);
	//������ѯ
	public MaterialType findById(int id);
	
	List<MaterialType> selectList();
}
