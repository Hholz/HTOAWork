package com.ht.serviceImpl.dailyWork;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ht.export.dailyWork.AttendancesExportModel;
import com.ht.export.student.StudrewardExportModel;
import com.ht.importexcel.vo.ImportMsgVo;
import com.ht.mapper.dailyWork.AttendancesMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.mapper.sysSet.FinanceAttencesetMapper;
import com.ht.popj.dailyWork.Attendances;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentReward;
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.service.dailyWork.AttendanceService;
import com.ht.util.ExportExcel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendancesMapper attendancesMapper;
	@Autowired
	EmpMapper empMapper;
	@Autowired
	FinanceAttencesetMapper financeAttencesetMapper;
	
	@Override
	public int insertSelective(Attendances record) {
		// TODO Auto-generated method stub
		return attendancesMapper.insertSelective(record);
	}

	@Override
	public List<Attendances> attendanceselect(Attendances record) {
		// TODO Auto-generated method stub
		return attendancesMapper.attendanceselect(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Attendances record) {
		// TODO Auto-generated method stub
		return attendancesMapper.updateByPrimaryKeySelective(record);
	}

	//导出考勤数据
	@Override
	public boolean exportExcel(List<Attendances> list, OutputStream out,
			String sheetName) {
		ExportExcel<AttendancesExportModel> exportExcel = new ExportExcel<AttendancesExportModel>();
		List<AttendancesExportModel> modelList = new ArrayList<AttendancesExportModel>();
		AttendancesExportModel rowModel = null;
		for (Attendances pojo : list) {
			rowModel = new AttendancesExportModel();
			Emp emp = empMapper.selectByPrimaryKey(pojo.getEmpid());
			rowModel.setEmpid(emp.getEmpname());
			FinanceAttenceset fin = financeAttencesetMapper.selectByPrimaryKey(Integer.parseInt(pojo.getAttentime()));
			rowModel.setAttentime(fin.getRemark());
			rowModel.setWorkday(pojo.getWorkday());
			rowModel.setFlag(pojo.getFlag());
			rowModel.setRemark(pojo.getRemark());
			modelList.add(rowModel);
		}
		exportExcel.exportExcel(sheetName,
				AttendancesExportModel.class.getName(), modelList, out);
		return true;
	}
	
	//打卡数据导入
	@Override
	public JSONObject importStudAtten(MultipartFile file) {
		// TODO Auto-generated method stub
		JSONObject results = new JSONObject();
		try {
    		Workbook workbook = Workbook.getWorkbook(file.getInputStream());//获得一个工作表对象
			Sheet sheet0 = workbook.getSheet(0);//取得第一个工作表，也可用sheet名字获得。
			int row0 = sheet0.getRows();//取得行数
			List<ImportMsgVo> importMsgVos = new ArrayList<>();
			List<Attendances> studr = new ArrayList<>();
			if(row0 <= 100) {//row0是指导入的Excel表总共有多少行数据----注意：导入的时候要看清Excel表是否对应数据库的表，字段是否为空
				//int endRow = sheet0.getRows();
				for (int i = 1; i < row0; i++) {
					Attendances attendances = new Attendances();
					
//							Cell cellt = sheet0.getCell(i,4);
//							String cellcon="";
//							if(cellt.getType() == CellType.DATE){
//							DateCell dc = (DateCell)cellt;
//							Date date = dc.getDate();
//							SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
//							  cellcon = ds.format(date);
//							}
//							studreward.setCreateTime(cellcon);
					
					Cell[] cells = sheet0.getRow(i);//	cell[]在excel中的每一竖列
					String c0 = defaultValueTrim(cells[0].getContents(), "");//获取第一行，第一列字段的值
					Emp emp = new Emp();
					emp.setEmpname(c0);
					List<Emp> emplist = empMapper.selectEmp(emp);
					String id = emplist.get(0).getId();
					attendances.setEmpid(id);
					
//					String c1 = defaultValueTrim(cells[1].getContents(), "");//获取第一行，第二列字段的值
//					attendances.setCreateTime(c1);
					
					String c2 = defaultValueTrim(cells[2].getContents(), "");
					attendances.setFlag(c2);
					
					String c3 = defaultValueTrim(cells[3].getContents(), "");
					attendances.setAttentime(c3);
					
					String c4 = defaultValueTrim(cells[4].getContents(), "");
					attendances.setRemark(c4);
					
					System.out.println("0=0=0=0");
					studr.add(attendances);
					
				}
				//批量调新增意向学生信息接口
				for (Attendances attendances : studr){
					ImportMsgVo importMsgVo = new ImportMsgVo(); 
					try {
						attendancesMapper.insertSelective(attendances);
						  importMsgVo.setOk("true");
						  importMsgVo.setStatus("打卡信息导入成功！");
				          // logger.info("意向报名信息导入成功,IrStudname="+inrestud.getIrStudname());
						  importMsgVos.add(importMsgVo);
						
					} catch (Exception e) {
						  importMsgVo.setOk("false");
						  importMsgVo.setStatus("打卡信息导入失败！");
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
	
}
