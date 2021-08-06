package com.scrh.money.web.controller;

import com.scrh.money.exterface.domain.LoanInfo;
import com.scrh.money.exterface.service.LoanInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 更多产品展示控制层
 * @author ScrH0052
 * @date 2021/8/3
 */
@Controller
@RequestMapping("/loan")
public class LoanInfoController {
    @DubboReference(interfaceClass = LoanInfoService.class,version = "0.0.1",timeout = 20000 )
    private LoanInfoService loanInfoService;

    /**
     *
     * @param pm 分页模型
     * @param mv 模型及视图
     * @return mv
     */
    @RequestMapping("/moreLoan")
    public ModelAndView moreLoan(@RequestParam(defaultValue = "1") Integer targetPage,
                                 @RequestParam(defaultValue = "1") Integer type,
                                 ModelAndView mv) {
        System.out.println("==========LOAN=========");

        //进行查询，并设置其余页面属性
        Map<String,Object> result = loanInfoService.queryLoanProductByTypeCode(targetPage, type);

        mv.addObject("loanInfoList", result.get("loanInfoList"));
        mv.addObject("pageInfo",result.get("pageInfo"));

        //添加产品种类信息
        String loanType = "";
        if (1 == type) {
            loanType = "优选标";
        } else if (2 == type) {
            loanType = "散标";
        }
        mv.addObject("loanType", loanType);
        mv.addObject("loanTypeCode", type);
        //添加视图
        mv.setViewName("/loan");
        return mv;
    }

    @RequestMapping("/loanInfo/{loanId}")
    public ModelAndView toLoanInfo(@PathVariable("loanId")Long id, ModelAndView mv){

        //根据id查询loanInfo
        LoanInfo loanInfo = loanInfoService.queryLoanInfoById(id);
        mv.addObject("loanInfo", loanInfo);

        //添加视图
        mv.setViewName("loanInfo");

        return mv;
    }
}
