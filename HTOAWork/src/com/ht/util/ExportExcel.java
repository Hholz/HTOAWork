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
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {
 //   private Logger log = LoggerFactory.getLogger(ExportExcel.class);

    public void exportExcel(Collection<T> dataset, OutputStream out) {
        exportExcel("导出EXCEL文档", null, dataset, out);
    }

    /**
     * 用于导出常规List，标题单列，无合并，List单列，无合并
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param className
     *            导成Excel的实体BEAN包名.类名
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    @SuppressWarnings("resource")
	public boolean exportExcel(String title, String className, Collection<T> dataset,
                               OutputStream out) {

        boolean success = true;

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);

        /***********************设置列表头的样式start****************************/
        // 列头样式
        HSSFCellStyle style = workbook.createCellStyle();

        // 背景颜色
        style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        // 设置居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 设置自动换行
        style.setWrapText(true);

        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式
        style.setFont(font);
        /***********************设置列表头的样式end****************************/

        /***********************设置正文的样式start****************************/
        // 正文样式
        HSSFCellStyle textStyle = workbook.createCellStyle();

        // 设置边框
        textStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        textStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        textStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        textStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        // 设置居中
        textStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        textStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 正文字体
        HSSFFont textFont = workbook.createFont();
        textFont.setColor(HSSFColor.BLACK.index);
        textFont.setFontHeightInPoints((short) 8);
        textFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        // 把字体应用到当前的样式
        textStyle.setFont(textFont);

        // 设置自动换行
        textStyle.setWrapText(true);
        /***********************设置列表头的样式end****************************/

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);

        // 获取属性
        Class<? extends Object> clasVo = null;

        try {
            clasVo = Class.forName(className);

            Field[] fields = getFields(clasVo.getDeclaredFields());

            // 按照注解id排序Excel列
            Arrays.sort(fields, new FieldComparator<ExcelAnnotation>());

            int indexI = 0;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                    // 获取该字段的注解对象
                    ExcelAnnotation anno = field.getAnnotation(ExcelAnnotation.class);
                    // 设置列宽
                    sheet.setColumnWidth(indexI, (short) (anno.width() * 35.7));
                    HSSFCell cell = row.createCell(indexI);
                    cell.setCellStyle(style);
                    HSSFRichTextString text = new HSSFRichTextString(anno.name());
                    cell.setCellValue(text);
                    indexI++;
                }
            }

            // 遍历集合数据，产生数据行
            Iterator<T> it = dataset.iterator();
            int index = 0;
            // 根据配置,重新设置列对齐方式
            //优化导出上限 不要放循环里
            HSSFCellStyle tempStyle = workbook.createCellStyle();
            tempStyle.cloneStyleFrom(textStyle);
            HSSFFont redFfont = workbook.createFont();
            HSSFFont greyFfont = workbook.createFont();
            //优化导出上限
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
                        // 获取该字段的注解对象
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

                        if (value.contains("↑")) {
                            richString.applyFont(redFfont);
                        } else if (value.contains("↓")) {
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
        // 获取含有excel注解的属性
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
