package com.ht.controller.student;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentLayer;
import com.ht.service.student.StudentFloorService;
import com.ht.service.student.StudentLayerService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

@Controller
@RequestMapping("/student/FloorLayer")
public class Floor_Layer_Controller {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	StudentFloorService studentFloorService;
	@Autowired
	StudentLayerService studentLayerService;
	@RequestMapping("/Floor")
	@SystemControllerLog(description = "����¥��¥��ҳ��")
	public String Floor(Model model) throws Exception{
		log.info(getClass()+"����¥��ҳ��");
		List<StudentFloor> studentFloorList = studentFloorService.selectStudentFloorAll();
		model.addAttribute("studentFloorList", studentFloorList);
		if (null != UserInfoUtil.getEmp()) {
			
		}
		return "/student/student_Floor_Layer";
	}
	
//bootstrop table ���url������ȡJson����
	@RequestMapping("/listFloorJson")
	//�˴�Ϊ��¼AOP����Controller��¼�û�����    
	@SystemControllerLog(description = "¥��controller���list��")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,StudentFloor stuFloor){
		ResultMessage rm = new ResultMessage();
		List<StudentFloor> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(stuFloor.getId()!=null){
			sList = studentFloorService.selectByDynamic(stuFloor);
		}else{
			sList = studentFloorService.selectStudentFloorAll();
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentFloor> pageInfo = new PageInfo<StudentFloor>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
       // System.err.println(sList.get(0).getFloorAdmin());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addFloor")
	public @ResponseBody int addFloor(Model model,StudentFloor floor,int layerNum )throws Exception{
		int count=0;
		if(null!=floor && layerNum!=0){
			int num = layerNum;
			count = studentFloorService.insertSelective(floor);
			StudentLayer layer;
			for (int i=1;i<=num;i++) {
				layer = new StudentLayer();
				layer.setFloorid(floor.getId());
				layer.setLayername(String.valueOf(i));
				studentLayerService.insertSelective(layer);
			}
				
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
 //����ѧ��¥��
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentFloor(Model model,StudentFloor studentFloor){  
		if(null!=studentFloor){
			int count = studentFloorService.updateByPrimaryKeySelective(studentFloor);
			return count;
		}
		return 0;
	}
	//ɾ��¥����ʵ�������޸�״̬����
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentFloor(Model model,@PathVariable("id")Integer id){
		//ʵ�����޸�״̬�������޸ķ���
		int count = studentFloorService.updateStatusPrimaryKey(id);
		return count;
	}
	
	
	//������¥���controller============================================================================
	@RequestMapping("/listLayerJson")
	//�˴�Ϊ��¼AOP����Controller��¼�û�����    
		@SystemControllerLog(description = "¥��controller���list��")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,StudentLayer stuLayer){
		ResultMessage rm = new ResultMessage();
		List<StudentLayer> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(stuLayer!=null){
			sList = studentLayerService.selectDynamic(stuLayer);
		}else{
			sList = studentLayerService.selectDynamic(stuLayer);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentLayer> pageInfo = new PageInfo<StudentLayer>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
        //System.err.println(sList.get(0).getFloorname());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	@RequestMapping("/addLayer")
	public @ResponseBody int addLayer(Model model,StudentLayer layer)throws Exception{
		if(null!=layer){
			int count = studentLayerService.insert(layer);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	} 
		
	
}
