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

    private String[] tables = new String[]{"device_base","user_base","user_login","daily_data","user_report","channel_report"};

    public TableRsp doInitReportTables(String appId)
    {
        for (int i = 0; i < tables.length; i++)
        {
            String tbName = appId + "_" + tables[i];

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
        if (templateName == "device_base")
            return SqlCode.getDeviceBase(tbName);

        if (templateName == "user_base")
            return SqlCode.getUserBase(tbName);

        if (templateName == "user_login")
            return SqlCode.getUserLogin(tbName);

        if (templateName == "daily_data")
            return SqlCode.getDailyData(tbName);

        if (templateName == "user_report")
            return SqlCode.getUserReport(tbName);

        if (templateName == "channel_report")
            return SqlCode.getChannelReport(tbName);

        return "";
    }
}
