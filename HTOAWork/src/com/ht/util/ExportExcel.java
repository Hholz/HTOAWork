package com.ht.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author Alvin
 * @version v1.0
 * @param <T>
 *            Ӧ�÷��ͣ���������һ������javabean������
 *            ע������Ϊ�˼������boolean�͵�����xxx��get����ʽΪgetXxx(),������isXxx()
 *            byte[]��jpg��ʽ��ͼƬ����
 */
public class ExportExcel<T> {
 //   private Logger log = LoggerFactory.getLogger(ExportExcel.class);

    public void exportExcel(Collection<T> dataset, OutputStream out) {
        exportExcel("����EXCEL�ĵ�", null, dataset, out);
    }

    /**
     * ���ڵ�������List�����ⵥ�У��޺ϲ���List���У��޺ϲ�
     * ����һ��ͨ�õķ�����������JAVA�ķ�����ƣ����Խ�������JAVA�����в��ҷ���һ��������������EXCEL ����ʽ�����ָ��IO�豸��
     * 
     * @param title
     *            ��������
     * @param className
     *            ����Excel��ʵ��BEAN����.����
     * @param dataset
     *            ��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���javabean������Ķ��󡣴˷���֧�ֵ�
     *            javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����)
     * @param out
     *            ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ�����������
     */
    @SuppressWarnings("resource")
	public boolean exportExcel(String title, String className, Collection<T> dataset,
                               OutputStream out) {

        boolean success = true;

        // ����һ��������
        HSSFWorkbook workbook = new HSSFWorkbook();

        // ����һ�����
        HSSFSheet sheet = workbook.createSheet(title);

        // ���ñ��Ĭ���п��Ϊ15���ֽ�
        sheet.setDefaultColumnWidth(15);

        /***********************�����б�ͷ����ʽstart****************************/
        // ��ͷ��ʽ
        HSSFCellStyle style = workbook.createCellStyle();

        // ������ɫ
        style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // ���ñ߿�
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        // ���þ���
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // �����Զ�����
        style.setWrapText(true);

        // ����һ������
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // ������Ӧ�õ���ǰ����ʽ
        style.setFont(font);
        /***********************�����б�ͷ����ʽend****************************/

        /***********************�������ĵ���ʽstart****************************/
        // ������ʽ
        HSSFCellStyle textStyle = workbook.createCellStyle();

        // ���ñ߿�
        textStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        textStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        textStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        textStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        // ���þ���
        textStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        textStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // ��������
        HSSFFont textFont = workbook.createFont();
        textFont.setColor(HSSFColor.BLACK.index);
        textFont.setFontHeightInPoints((short) 8);
        textFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        // ������Ӧ�õ���ǰ����ʽ
        textStyle.setFont(textFont);

        // �����Զ�����
        textStyle.setWrapText(true);
        /***********************�����б�ͷ����ʽend****************************/

        // ������������
        HSSFRow row = sheet.createRow(0);

        // ��ȡ����
        Class<? extends Object> clasVo = null;

        try {
            clasVo = Class.forName(className);

            Field[] fields = getFields(clasVo.getDeclaredFields());

            // ����ע��id����Excel��
            Arrays.sort(fields, new FieldComparator<ExcelAnnotation>());

            int indexI = 0;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                    // ��ȡ���ֶε�ע�����
                    ExcelAnnotation anno = field.getAnnotation(ExcelAnnotation.class);
                    // �����п�
                    sheet.setColumnWidth(indexI, (short) (anno.width() * 35.7));
                    HSSFCell cell = row.createCell(indexI);
                    cell.setCellStyle(style);
                    HSSFRichTextString text = new HSSFRichTextString(anno.name());
                    cell.setCellValue(text);
                    indexI++;
                }
            }

            // �����������ݣ�����������
            Iterator<T> it = dataset.iterator();
            int index = 0;
            // ��������,���������ж��뷽ʽ
            //�Ż��������� ��Ҫ��ѭ����
            HSSFCellStyle tempStyle = workbook.createCellStyle();
            tempStyle.cloneStyleFrom(textStyle);
            HSSFFont redFfont = workbook.createFont();
            HSSFFont greyFfont = workbook.createFont();
            //�Ż���������
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                Class<? extends Object> clazz = t.getClass();

                int indexJ = 0;
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                        HSSFCell cell = row.createCell(indexJ);
                        // ��ȡ���ֶε�ע�����
                        ExcelAnnotation anno = field.getAnnotation(ExcelAnnotation.class);
                        tempStyle.setAlignment((short) anno.align());
                        cell.setCellStyle(tempStyle);

                        String methodName = "get" + field.getName().substring(0, 1).toUpperCase()
                                            + field.getName().substring(1);

                        Method getMethod = clazz.getMethod(methodName);
                        Object objValue = getMethod.invoke(t);
                        String value = objValue == null ? "" : objValue.toString();

                        HSSFRichTextString richString = new HSSFRichTextString(value);

                        // RED font
              
                        redFfont.setColor(HSSFColor.RED.index);

                        // GREY font
                       
                        greyFfont.setColor(HSSFColor.GREY_50_PERCENT.index);

                        if (value.contains("��")) {
                            richString.applyFont(redFfont);
                        } else if (value.contains("��")) {
                            richString.applyFont(greyFfont);
                        }

                        cell.setCellValue(richString);
                        indexJ++;
                    }
                }
            }
            workbook.write(out);
            out.flush();
        } catch (ClassNotFoundException e) {
            //log.error(e.getMessage());
            success = false;
        } catch (SecurityException e) {
           // log.error(e.getMessage());
            success = false;
        } catch (IllegalArgumentException e) {
            //log.error(e.getMessage());
            success = false;
        } catch (IOException e) {
            //log.error(e.getMessage());
            success = false;
        } catch (NoSuchMethodException e) {
            //log.error(e.getMessage());
            success = false;
        } catch (IllegalAccessException e) {
           // log.error(e.getMessage());
            success = false;
        } catch (InvocationTargetException e) {
           // log.error(e.getMessage());
            success = false;
        } catch (Exception e) {
           // log.error(e.getMessage());
            success = false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                //log.error(e.getMessage());
                success = false;
            }
        }
        return success;
    }

    private Field[] getFields(Field[] fields) {

        List<Field> fs = new ArrayList<Field>();
        // ��ȡ����excelע�������
        for (Field f : fields) {
            if (f.getAnnotation(ExcelAnnotation.class) != null) {
                fs.add(f);
            }
        }
        Field[] fis = new Field[fs.size()];
        for (int i = 0; i < fs.size(); i++) {
            fis[i] = fs.get(i);
        }

        return fis;
    }
}
