package com.ht.controller.conn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentLayer;
import com.ht.service.student.StudentFloorService;
import com.ht.service.student.StudentLayerService;

@Controller
@RequestMapping("/conn")
public class GetHouController {

	@Autowired
	StudentLayerService studentLayerService;
	@Autowired
	StudentFloorService studentFloorService;
	//获取所有楼栋
	@RequestMapping("/AllFloor")
	public @ResponseBody List<StudentFloor> AllFall() throws Exception{  
		List<StudentFloor> floorList = new ArrayList<StudentFloor>();
		floorList = studentFloorService.selectStudentFloorAll();
		return floorList;
	}
	//通过楼栋id来获取所有层
	@RequestMapping(value = "/AllLayer/{id}")
	public @ResponseBody List<StudentLayer> AllCls(@PathVariable("id")Integer id) throws Exception{  
		List<StudentLayer> layerList = new ArrayList<StudentLayer>();
		StudentLayer layer = new StudentLayer();
		layer.setFloorid(id);
		layerList = studentLayerService.selectDynamic(layer);
		return layerList;
	}
}
