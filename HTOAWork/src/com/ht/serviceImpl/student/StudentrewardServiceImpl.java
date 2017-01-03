package com.ht.serviceImpl.student;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;




import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ht.export.student.StudrewardExportModel;
import com.ht.importexcel.vo.ImportMsgVo;
import com.ht.mapper.student.StudentRewardMapper;
import com.ht.popj.student.StudentReward;
import com.ht.popj.student.StudentSayface;
import com.ht.service.student.StudentrewardService;
import com.ht.util.ExportExcel;

public class StudentrewardServiceImpl implements StudentrewardService{

	@Autowired
	StudentRewardMapper studentRewardMapper;
	
	@Override
	public List<StudentReward> studentrewardsel(StudentReward record) {
		// TODO Auto-generated method stub
		return studentRewardMapper.studentrewardsel(record);
	}

	@Override
	public int insertSelective(StudentReward record) {
		// TODO Auto-generated method stub
		return studentRewardMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StudentReward record) {
		// TODO Auto-generated method stub
		return studentRewardMapper.updateByPrimaryKeySelective(record);
	}

	
	 
	//导出意向学生业务实现
	@Override
	public boolean exportExcel(List<StudentReward> list, OutputStream out,
			String sheetName) {
		// TODO Auto-generated method stub
		ExportExcel<StudrewardExportModel> exportExcel = new ExportExcel<StudrewardExportModel>();
		List<StudrewardExportModel> modelList = new ArrayList<StudrewardExportModel>();
		StudrewardExportModel rowModel = null;
		for (StudentReward pojo : list) {
			rowModel = new StudrewardExportModel();
			rowModel.setStudentid(pojo.getStudentid());
			rowModel.setReason(pojo.getReason());
			rowModel.setContent(pojo.getContent());
			rowModel.setCreateTime(pojo.getCreateTime());
			modelList.add(rowModel);
		}
		exportExcel.exportExcel(sheetName,
				StudrewardExportModel.class.getName(), modelList, out);
		return true;
	}

	@Override
	public JSONObject importStudReward(MultipartFile file) {
		// TODO Auto-generated method stub
		JSONObject results = new JSONObject();
		try {
    		Workbook workbook = Workbook.getWorkbook(file.getInputStream());//获得一个工作表对象
			Sheet sheet0 = workbook.getSheet(0);//取得第一个工作表，也可用sheet名字获得。
			int row0 = sheet0.getRows();//取得行数
			List<ImportMsgVo> importMsgVos = new ArrayList<>();
			List<StudentReward> studr = new ArrayList<>();
			if(row0 <= 100) {//row0是指导入的Excel表总共有多少行数据----注意：导入的时候要看清Excel表是否对应数据库的表，字段是否为空
				//int endRow = sheet0.getRows();
				for (int i = 1; i < row0; i++) {
					StudentReward studreward = new StudentReward();
					
//					Cell cellt = sheet0.getCell(i,4);
//					String cellcon="";
//					if(cellt.getType() == CellType.DATE){
//					DateCell dc = (DateCell)cellt;
//					Date date = dc.getDate();
//					SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
//					  cellcon = ds.format(date);
//					}
//					studreward.setCreateTime(cellcon);
					
					Cell[] cells = sheet0.getRow(i);//	cell[]在excel中的每一竖列
					String c0 = defaultValueTrim(cells[0].getContents(), "");//获取第一行，第一列字段的值
					studreward.setStudentid(Integer.parseInt(c0));
					String c1 = defaultValueTrim(cells[1].getContents(), "");//获取第一行，第二列字段的值
					studreward.setReason(c1);
					String c2 = defaultValueTrim(cells[2].getContents(), "");
					studreward.setContent(c2);
//					String c3 = defaultValueTrim(cells[3].getContents(), "");
//					studreward.setCreateTime(c3);
					
					System.out.println("0=0=0=0");
					studr.add(studreward);
					
				}
				//批量调新增意向学生信息接口
				for (StudentReward studreward : studr){
					ImportMsgVo importMsgVo = new ImportMsgVo(); 
					try {
						studentRewardMapper.insertSelective(studreward);
						  importMsgVo.setOk("true");
						  importMsgVo.setStatus(studreward.getStudentid()+",意向报名信息导入成功！");
				          // logger.info("意向报名信息导入成功,IrStudname="+inrestud.getIrStudname());
						  importMsgVos.add(importMsgVo);
						
					} catch (Exception e) {
						  importMsgVo.setOk("false");
						  importMsgVo.setStatus(studreward.getStudentid()+",意向报名信息导入失败！");
				          //logger.info("意向报名信息导入失败,IrStudname="+inrestud.getIrStudname(),e.printStackTrace());
						  importMsgVos.add(importMsgVo);
					}
				}
				results.put("importResults", importMsgVos);
			}else{
				ImportMsgVo importMsgVo = new ImportMsgVo(); 
				importMsgVo.setOk("false");
				importMsgVo.setStatus("一次导入意向报名信息不能超过100条。");
				importMsgVos.add(importMsgVo);
				results.put("importResults", importMsgVos);
			}   
			workbook.close();
       }catch (Exception e){
         // logger.error("导入意向学生信息错误,fileName=" + file.getOriginalFilename(),e);
       }
       return results;
		
	}

	private String defaultValueTrim(String source, String defaultValue) {
		if (!StringUtils.isEmpty(source))
			return source.trim();
		else {
			return defaultValue;
		}
	}

	@Override
	public List<StudentReward> selectByStuId(Integer stuId) {
		return studentRewardMapper.selectByStuId(stuId);
	}

}
