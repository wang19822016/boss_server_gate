package com.seastar.service;

import com.seastar.entity.TableRsp;

/**
 * Created by e on 2017/1/23.
 */
public interface TableService
{
    TableRsp doInitReportTables(String appId);
    TableRsp doDestroyReportTables(String appId);
}
