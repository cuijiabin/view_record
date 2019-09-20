package com.rose.bob.log.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/expressCompany")
public class ExpressCompanyController{

    @RequestMapping(value = "/getList.ajax", method = RequestMethod.GET)
    @ResponseBody
    public Map getList(HttpServletRequest request) {
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("code","0");
        return resultMap;
    }
}
