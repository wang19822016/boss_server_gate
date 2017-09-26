package com.seastar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seastar.common.ReturnCode;
import com.seastar.entity.*;
import com.seastar.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lj on 2017/1/15.
 */
@RestController
public class ReceiveDataController
{
    @Autowired
    private TableService tableService;

//    private Logger logger = LoggerFactory.getLogger(ReceiveDataController.class);
//    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/report/init_tables", method = RequestMethod.POST)
    public void OnInitTablesByGameId(@RequestBody TableReq req, HttpServletRequest request)
    {
        System.out.println(req.api + " / " + req.appId);

        TableRsp rsp = tableService.doInitReportTables(req.appId);

        if (rsp.code == ReturnCode.CODE_TABLE_INIT_OK)
        {
            System.out.println("数据表创建成功！");
        }
        else
        {
            System.out.println("数据表创建失败！");
        }
    }
//
//    @RequestMapping(value = "/report/destroy_tables", method = RequestMethod.POST)
//    public void OnDestroyTablesByGameId(@RequestBody DeviceInstallReq req, HttpServletRequest request)
//    {
//
//    }
}
