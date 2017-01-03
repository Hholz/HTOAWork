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

	//������������
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
	
	//�����ݵ���
	@Override
	public JSONObject importStudAtten(MultipartFile file) {
		// TODO Auto-generated method stub
		JSONObject results = new JSONObject();
		try {
    		Workbook workbook = Workbook.getWorkbook(file.getInputStream());//���һ�����������
			Sheet sheet0 = workbook.getSheet(0);//ȡ�õ�һ��������Ҳ����sheet���ֻ�á�
			int row0 = sheet0.getRows();//ȡ������
			List<ImportMsgVo> importMsgVos = new ArrayList<>();
			List<Attendances> studr = new ArrayList<>();
			if(row0 <= 100) {//row0��ָ�����Excel���ܹ��ж���������----ע�⣺�����ʱ��Ҫ����Excel���Ƿ��Ӧ���ݿ�ı��ֶ��Ƿ�Ϊ��
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
					
					Cell[] cells = sheet0.getRow(i);//	cell[]��excel�е�ÿһ����
					String c0 = defaultValueTrim(cells[0].getContents(), "");//��ȡ��һ�У���һ���ֶε�ֵ
					Emp emp = new Emp();
					emp.setEmpname(c0);
					List<Emp> emplist = empMapper.selectEmp(emp);
					String id = emplist.get(0).getId();
					attendances.setEmpid(id);
					
//					String c1 = defaultValueTrim(cells[1].getContents(), "");//��ȡ��һ�У��ڶ����ֶε�ֵ
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
				//��������������ѧ����Ϣ�ӿ�
				for (Attendances attendances : studr){
					ImportMsgVo importMsgVo = new ImportMsgVo(); 
					try {
						attendancesMapper.insertSelective(attendances);
						  importMsgVo.setOk("true");
						  importMsgVo.setStatus("����Ϣ����ɹ���");
				          // logger.info("��������Ϣ����ɹ�,IrStudname="+inrestud.getIrStudname());
						  importMsgVos.add(importMsgVo);
						
					} catch (Exception e) {
						  importMsgVo.setOk("false");
						  importMsgVo.setStatus("����Ϣ����ʧ�ܣ�");
				          //logger.info("��������Ϣ����ʧ��,IrStudname="+inrestud.getIrStudname(),e.printStackTrace());
						  importMsgVos.add(importMsgVo);
					}
				}
				results.put("importResults", importMsgVos);
			}else{
				ImportMsgVo importMsgVo = new ImportMsgVo(); 
				importMsgVo.setOk("false");
				importMsgVo.setStatus("һ�ε�����������Ϣ���ܳ���100����");
				importMsgVos.add(importMsgVo);
				results.put("importResults", importMsgVos);
			}   
			workbook.close();
       }catch (Exception e){
         // logger.error("��������ѧ����Ϣ����,fileName=" + file.getOriginalFilename(),e);
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
