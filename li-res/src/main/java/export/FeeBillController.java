package com.sprucetec.tms.controller.export;

import com.sprucetec.tms.controller.base.FeeBillExportExcel;
import com.sprucetec.tms.distribute.app.entity.AppResult;
import com.sprucetec.tms.distribute.platform.entity.FeeBill;
import com.sprucetec.tms.model.FeeBillVo;
import com.sprucetec.tms.utils.Ret;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 费用对账单
 */
@Controller
@RequestMapping("/feeBill")
public class FeeBillController {

    private static final Log logger = LogFactory.getLog(FeeBillController.class);

    @RequestMapping("/export")
    public AppResult<Object> export(FeeBillVo vo, HttpServletRequest request, HttpServletResponse response) {
        //搜索参数
        if (StringUtils.isBlank(vo.getStDt()) || StringUtils.isBlank(vo.getEtDt())) {
            return Ret.retFalse("请选择配送日期");
        }


        List<FeeBill> feeBillList = new ArrayList<>();
        //feeBillList add 数据
        String format = "yyyy-MM-dd";

        //导出数据
        Map<String, Map<String, String>> colValueToChange = new HashMap<>();

        FeeBillExportExcel ex = new FeeBillExportExcel(feeBillList, "费用对账", "费用对账1");
        ex.setColValueToChange(colValueToChange);
        ex.exportExcel(response);

        return Ret.retTrue("导出成功");
    }

}