package com.sprucetec.tms.controller.export;

import com.sprucetec.tms.distribute.platform.entity.FeeBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class FeeBillExportExcel extends ExportExcel<FeeBill> {

	/**
	 *
	 * @param dataset 数据源
	 *            导出路径
	 * @param fileName
	 *            导出文件名称
	 * @param title
	 *            文件标题
	 */

	public FeeBillExportExcel(Collection<FeeBill> dataset,
							  String fileName, String title) {

		super(dataset);
		super.setTitle(title);// 标题，以查询的起止日期作为标题
		super.setFileName(fileName);// Excel文件名(以站点名作为文件名)

		/* 设置Excel基本数据 */

		// 定义列名与类属性名的对应关系
		//
		List<ColnameToField> colNameToField = new ArrayList<ColnameToField>() {
			{
				add(new ColnameToField("userNo","司机编号/司机姓名"));
				add(new ColnameToField("userSourceName","来源"));
				add(new ColnameToField("taskbillNo","任务单号"));
				add(new ColnameToField("taskType","任务类型"));
				add(new ColnameToField("remark","备注(运费)"));
			}
		};

		super.setColNameToField(colNameToField);
	}

}
