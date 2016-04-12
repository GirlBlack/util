package com.sprucetec.tms.controller.export;

import com.sprucetec.tms.utils.PubAppUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 
 * @author lili
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 */
public  class ExportExcel<T> {
	private static final Log logger = LogFactory.getLog(ExportExcel.class);

	/**
	 * xls数据映射类
	 */
	private Class<T> classType;

	private Collection<T> dataset;

	private String fileName;// 文件名

	private String title;// 文档标题


	private List<ColnameToField> colNameToField;// 列名与类属性名的对应关系

	private Map<String, Map<String, String>> colValueToChange;// 将列值转换为另外的值

	/**
	 * 构造方法，初始化数据
	 * 
	 * @param dataset
	 *            数据集合
	 *            Tomcat项目运行路径
	 */
	@SuppressWarnings("unchecked")
	public ExportExcel(Collection<T> dataset) {
		try {
			// 获取泛型的实际类型
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				// 转换成泛型类
				ParameterizedType pType = (ParameterizedType) type;
				this.classType = (Class<T>) pType.getActualTypeArguments()[0];
			}
			this.dataset = dataset;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setExportExcel(Collection<T> dataset) {
		try {
			// 获取泛型的实际类型
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				// 转换成泛型类
				ParameterizedType pType = (ParameterizedType) type;
				this.classType = (Class<T>) pType.getActualTypeArguments()[0];
			}
			this.dataset = dataset;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setColNameToField(List<ColnameToField> colNameToField) {
		this.colNameToField = colNameToField;
	}

	public void setColValueToChange(
			Map<String, Map<String, String>> colValueToChange) {
		this.colValueToChange = colValueToChange;
	}

	/**
	 * 生成Excel文件
	 * @param response
	 */
	public void exportExcel(HttpServletResponse response) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(fileName);

		// 设置表格标题行的列名数组并生成Excel
		int i = 0;
		Row titleRow = sheet.createRow(0);
		for (ColnameToField colField : colNameToField) {
			titleRow.createCell(i).setCellValue(colField.getColname());
			i++;
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			Row row = sheet.createRow(index);
			T t = (T) it.next();// 取出每个对象
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (int j= 0; j<colNameToField.size(); j++) {
				ColnameToField colField = colNameToField.get(j);
				String fieldName = colField.getFieldName();// 取属性名

				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Method method = classType.getMethod(getMethodName,
							new Class[]{});
					Object value = method.invoke(t, new Object[]{});
					String textValue = String.valueOf(value);
					if (value instanceof Integer) {
						textValue = String.valueOf(PubAppUtil.ifNull((Integer) value));
					} else if (value instanceof Float) {
						textValue = String.valueOf(PubAppUtil.ifNull((Float) value));

					} else if (value instanceof Double) {
						textValue = String.valueOf(PubAppUtil.ifNull((Double) value));
					} else if (value instanceof Long) {
						textValue = String.valueOf(PubAppUtil.ifNull((Long) value));
					}
					// 判断值的类型后进行强制类型转换
					if (textValue == null || "null" == textValue) {
						textValue = "";
					}
					if (this.colValueToChange != null) {

						// 取出属性值转换Map
						Map<String, String> changeMap = colValueToChange
								.get(fieldName);
						if (changeMap != null && changeMap.size() > 0) {
							textValue = changeMap.get(textValue)== null ? "" :changeMap.get(textValue);
						}
					}
					row.createCell(j).setCellValue(textValue);
				} catch (Exception e) {
					logger.error("导出异常", e);
				}
			}
			//导出
		}
		doExportExcel(wb, fileName, response);
	}
	public void doExportExcel(Workbook wb, String excelFileName, HttpServletResponse response){
		OutputStream fOut = null;
		try {
			String fileName = URLEncoder.encode(excelFileName, "UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");
			fOut = response.getOutputStream();
			wb.write(fOut);
		} catch (IOException e) {
			logger.error("导出失败", e);
		}finally{
			try
			{
				logger.info("导出成功!关闭IO流~~~");
				fOut.flush();
				fOut.close();
				wb.close();
			}catch (IOException e)
			{
				logger.error("IO流close失败!");
			}
		}
	}
}
