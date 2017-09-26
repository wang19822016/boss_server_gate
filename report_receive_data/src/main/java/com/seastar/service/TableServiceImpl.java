package com.seastar.service;

import com.seastar.common.ReturnCode;
import com.seastar.common.SqlCode;
import com.seastar.dao.TableDao;
import com.seastar.entity.TableRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by e on 2017/1/23.
 */
@Service
public class TableServiceImpl implements TableService
{
    @Autowired
    private TableDao tableDao;

    private String[] tables = new String[]{"daily_data","goods","ltv","ltv_android","ltv_ios", "pay_conversion",
            "pay_conversion_android","pay_conversion_ios", "remain", "remain_android", "remain_ios", "roi",
            "roi_android", "roi_ios", "user_base","user_login","user_pay", "user_report","user_report_android","user_report_ios"};

    public TableRsp doInitReportTables(String appId)
    {
        for (int i = 0; i < tables.length; i++)
        {
            String tbName =  tables[i] + "_" + appId;

            if (!tableDao.isHaveTable(tbName))
                tableDao.createTable(getSql(tables[i], tbName));
        }

        TableRsp rsp = new TableRsp();
        rsp.code = ReturnCode.CODE_TABLE_INIT_OK;
        rsp.codeDesc = ReturnCode.CODE_TABLE_INIT_OK_DESC;
        return rsp;
    }

    public TableRsp doDestroyReportTables(String appId)
    {
        return null;
    }

    private String getSql(String templateName, String tbName)
    {
//        if (templateName == "device_base")
//            return SqlCode.getDeviceBase(tbName);

        if (templateName == "daily_data")
            return SqlCode.getDailyData(tbName);

        if (templateName == "goods")
            return SqlCode.getGoods(tbName);

        if (templateName == "ltv" || templateName == "ltv_android" || templateName == "ltv_ios")
            return SqlCode.getLtv(tbName);

        if (templateName == "pay_conversion" || templateName == "pay_conversion_android" || templateName == "pay_conversion_ios")
            return SqlCode.getPayConversion(tbName);

        if (templateName == "remain" || templateName == "remain_android" || templateName == "remain_ios")
            return SqlCode.getRemain(tbName);

        if (templateName == "roi" || templateName == "roi_android" || templateName == "roi_ios")
            return SqlCode.getRoi(tbName);

        if (templateName == "user_base")
            return SqlCode.getUserBase(tbName);

        if (templateName == "user_login")
            return SqlCode.getUserLogin(tbName);

        if (templateName == "user_pay")
            return SqlCode.getUserPay(tbName);

        if (templateName == "user_report" || templateName == "user_report_android" || templateName == "user_report_ios")
            return SqlCode.getUserReport(tbName);

//        if (templateName == "channel_report")
//            return SqlCode.getChannelReport(tbName);

        return "";
    }
}
