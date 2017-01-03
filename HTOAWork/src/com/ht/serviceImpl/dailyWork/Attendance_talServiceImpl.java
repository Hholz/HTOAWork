package com.ht.serviceImpl.dailyWork;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ht.mapper.dailyWork.Attendance_talMapper;
import com.ht.mapper.sysSet.FinanceAttencesetMapper;
import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.service.dailyWork.Attendance_talService;
import com.ht.importexcel.vo.ImportMsgVo;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

public class Attendance_talServiceImpl implements Attendance_talService {
	@Autowired
	private Attendance_talMapper attendanceMapper;
	@Autowired
	private FinanceAttencesetMapper financeAttencesetMapper;

	@Override
	public List<Attendance_tal> attendancelist(Attendance_tal attendance_tal)
			throws Exception {
		List<Attendance_tal> tal = attendanceMapper
				.attendancelist(attendance_tal);
		return tal;
	}
	
	@Override
	public List<Attendance_tal> selectCount(){
		List<Attendance_tal> attendance_tals = new ArrayList<>();
		attendance_tals = attendanceMapper.selectCount();
		int count = attendanceMapper.selectMouthCount();
		List<Attendance_tal> attendance_tals1 = new ArrayList<>();
		Attendance_tal attendance_tal = new Attendance_tal();
		for (int i = 0; i < attendance_tals.size(); i++) {
			if (attendance_tals.get(i).getNum() == count) {
				attendance_tal = attendance_tals.get(i);
				attendance_tals1.add(attendance_tal);
			}
		}
		for(int i=0;i<attendance_tals1.size();i++){
			Attendance_tal attendance = new Attendance_tal();
			attendance.setFingerprint(attendance_tals1.get(i).getFingerprint());
			Date now = new Date();
			DateFormat d1 = DateFormat.getDateInstance();//��ʽ:YYYY��MM��DD��
			String date = d1.format(now).substring(0,7);
			attendance.setWorkday(date);
			try {
				//���ò�ѯ����
				List<Attendance_tal> attendances = attendanceMapper.attendancefind(attendance);
				for(int j=0;j<attendances.size();j++){
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					Date da = formatter.parse(attendances.get(j).getWorkday());
					int d = da.getMinutes();//��ʱ��-��
					// ����ʱ�����ñ�Ĳ�ѯ����
					FinanceAttenceset finance = financeAttencesetMapper
							.selectabjuct();
					// �ϰ�ʱ��
					Date das = formatter.parse(finance.getTime1());
					int sb = das.getMinutes();//�ϰ�ʱ��-��
					if(d>(sb-5)){
						attendance_tals1.remove(i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return attendance_tals1;
	}

	@Override
	public void insertInreStud(Attendance_tal attendance_tal) throws Exception {
		attendanceMapper.insertInreStud(attendance_tal);

	}

	@Override
	public Attendance_tal findAttendance(Attendance_tal attendance_tal)
			throws Exception {

		return attendanceMapper.findAttendance(attendance_tal);
	}

	@Override
	public void addattendance(Attendance_tal attendance_tal) throws Exception {
		attendanceMapper.addattendance(attendance_tal);

	}

	@Override
	public JSONObject importStudAtten(MultipartFile file) {
		JSONObject results = new JSONObject();
		try {
			Workbook workbook = Workbook.getWorkbook(file.getInputStream());
			Sheet sheet0 = workbook.getSheet(0);
			int row0 = sheet0.getRows();
			List<ImportMsgVo> importMsgVos = new ArrayList<>();
			for (int i = 1; i < row0; i++) {
				Attendance_tal inrestud = new Attendance_tal();
				Cell[] cells = sheet0.getRow(i);
				String c0 = defaultValueTrim(cells[0].getContents(), "");
				inrestud.setId(Integer.parseInt(c0));
				String c1 = defaultValueTrim(cells[1].getContents(), "");

				// Ա��ָ�Ʊ��
				String c2 = defaultValueTrim(cells[2].getContents(), "");
				inrestud.setFingerprint(Integer.parseInt(c2));

				String c3 = defaultValueTrim(cells[3].getContents(), "");
				String c4 = defaultValueTrim(cells[4].getContents(), "");
				Cell cji = cells[5];
				String nyr = FormateTime(cji);
				// System.out.println(c2+"  "+c3+"   "+nyr);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Date date = formatter.parse(nyr);
				int da = date.getHours();

				inrestud.setWorkday(nyr.substring(0, 10));

				String sfm = nyr.substring(11, 16);
				// ��ʱ��
				int dksj = Integer.parseInt(sfm.replace(":", ""));
				if (da <= 12) {
					inrestud.setType("a");
				} else if (da > 12) {
					inrestud.setType("p");
				}
				// ���ò�ѯ����--��ѯģ�������
				Attendance_tal attendance = attendanceMapper
						.findAttendance(inrestud);
				// ����ʱ�����ñ�Ĳ�ѯ����
				FinanceAttenceset finance = financeAttencesetMapper
						.selectabjuct();
				// �ϰ�ʱ��
				int sb = Integer
						.parseInt((finance.getTime1()).replace(":", "")) / 100;
				// �ٵ�ʱ��
				int cd = Integer
						.parseInt((finance.getTime2()).replace(":", "")) / 100;
				// �°�ʱ��
				int xb = Integer
						.parseInt((finance.getTime3()).replace(":", "")) / 100;
				ImportMsgVo importMsgVo = new ImportMsgVo();
				try {
					if (attendance.getStatus() != 0) {
						if (dksj <= cd) {
							if (dksj <= sb) {
								attendance.setStatus(4);
								attendance.setWorkday(nyr);
								// �����޸ĺ���
								attendanceMapper.insertInreStud(attendance);
							} else {
								attendance.setLate(dksj - sb);// ���óٵ�ʱ��
								attendance.setStatus(2);// ����״̬
								attendance.setWorkday(nyr);// ���ô�ʱ��
								// �����޸ĺ���
								attendanceMapper.insertInreStud(attendance);
							}
						} else if (dksj >= xb) {
							//
							if (attendance.getStatus() == 4) {
								Attendance_tal att = new Attendance_tal();
								att.setFingerprint(attendance.getFingerprint());
								att.setEmpname(attendance.getEmpname());
								att.setDepid(attendance.getDepid());
								att.setWorkday(nyr);
								att.setLate(attendance.getLate());
								att.setType(attendance.getType());
								att.setStatus(3);
								attendanceMapper.addattendance(att);
								importMsgVo.setOk("true");
								importMsgVo.setStatus("������Ϣ��������ɹ���");
							} else {
								attendance.setStatus(4);
								attendance.setWorkday(nyr);
								// �����޸ĺ���
								attendanceMapper.insertInreStud(attendance);
							}

						} else {
							Attendance_tal att = new Attendance_tal();
							att.setFingerprint(attendance.getFingerprint());
							att.setEmpname(attendance.getEmpname());
							att.setDepid(attendance.getDepid());
							att.setWorkday(nyr);
							att.setLate(attendance.getLate());
							att.setType(attendance.getType());
							att.setStatus(3);
							attendanceMapper.addattendance(att);
							importMsgVo.setOk("true");
							importMsgVo.setStatus(attendance.getEmpname()
									+ "   " + nyr + "������Ϣ��������ɹ���");
						}
					}else{
						Attendance_tal att = new Attendance_tal();
						att.setFingerprint(attendance.getFingerprint());
						att.setEmpname(attendance.getEmpname());
						att.setDepid(attendance.getDepid());
						att.setWorkday(nyr);
						att.setLate(attendance.getLate());
						att.setType(attendance.getType());
						att.setStatus(4);
						attendanceMapper.addattendance(att);
						importMsgVo.setOk("true");
						importMsgVo.setStatus(attendance.getEmpname()
								+ "   " + nyr + "������Ϣ��������ɹ���");
					}
					importMsgVo.setOk("true");
					importMsgVo.setStatus(attendance.getEmpname() + "   " + nyr
							+ "������Ϣ�޸ĵ���ɹ���");
					importMsgVos.add(importMsgVo);
				} catch (Exception e) {
					importMsgVo.setOk("false");
					importMsgVo.setStatus("����:" + attendance.getEmpname()
							+ "  ��ʱ��:" + nyr + "������Ϣ����ʧ�ܣ�");
					importMsgVos.add(importMsgVo);
				}
			}
			results.put("importResults", importMsgVos);
			workbook.close();
		} catch (Exception e) {
			System.out.print(e);
		}
		return results;
	}

	public static String FormateTime(Cell formatecell) {
		java.util.Date mydate = null;
		DateCell datecll = (DateCell) formatecell;
		mydate = datecll.getDate();
		long time = (mydate.getTime() / 1000) - 60 * 60 * 8;
		mydate.setTime(time * 1000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(mydate);
	}

	private String defaultValueTrim(String source, String defaultValue) {
		if (!StringUtils.isEmpty(source))
			return source.trim();
		else {
			return defaultValue;
		}
	}

	@Override
	public void deleteAttendance()
			throws Exception {
		attendanceMapper.deleteAttendance();

	}

	@Override
	public List<Attendance_tal> fAttendance(Attendance_tal attendance_tal)
			throws Exception {

		return attendanceMapper.fAttendance(attendance_tal);
	}

	@Override
	public List<Attendance_tal> normalAttendance() throws Exception {
		return attendanceMapper.normalAttendance();

	}

	@Override
	public List<Attendance_tal> abnormalAttendance() throws Exception {
		return attendanceMapper.abnormalAttendance();

	}

	@Override
	public List<Attendance_tal> absenteeism() throws Exception {
		return attendanceMapper.absenteeism();

	}

	@Override
	public List<Attendance_tal> lateAttendance() throws Exception {

		return attendanceMapper.lateAttendance();
	}

	@Override
	public List<Attendance_tal> findidAttendance(int tal) throws Exception {
		// TODO Auto-generated method stub
		return attendanceMapper.findidAttendance(tal);
	}
}
