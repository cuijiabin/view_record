package com.rose.bob.log.mvc;

import com.rose.bob.domain.FreightInsureOrderLog;
import com.rose.bob.service.FreightInsureOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/log")
public class LogController {

    @Autowired
    private FreightInsureOrderLogService freightInsureOrderLogService;

    @RequestMapping(value = "/{id}.ajax", method = {RequestMethod.GET})
    @ResponseBody
    public Map getList(HttpServletRequest request, @PathVariable Integer id) {

        FreightInsureOrderLog freightInsureOrderLog = freightInsureOrderLogService.selectByPrimaryKey(id);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("msg", freightInsureOrderLog.toString());

        return resultMap;
    }

    @RequestMapping(value = "/transaction/{id}.ajax", method = {RequestMethod.GET})
    @ResponseBody
    public Map transactionTest(HttpServletRequest request, @PathVariable Integer id) {

        // 无事务
        FreightInsureOrderLog freightInsureOrderLog = freightInsureOrderLogService.copyByPrimaryKey(id);
        // 开启事务
        freightInsureOrderLogService.insertByPrimaryKey(id);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("msg", freightInsureOrderLog.toString());

        return resultMap;
    }
}
