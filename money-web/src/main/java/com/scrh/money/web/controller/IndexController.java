package com.scrh.money.web.controller;

import com.scrh.money.exterface.domain.LoanInfo;
import com.scrh.money.exterface.service.BidInfoService;
import com.scrh.money.exterface.service.LoanInfoService;
import com.scrh.money.exterface.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * 首页控制层
 * @author ScrH0052
 * @date 2021/8/2
 */
@Controller
public class IndexController {
    @DubboReference(interfaceClass = UserService.class,version = "0.0.1",timeout = 20000 )
    private UserService userService;

    @DubboReference(interfaceClass = LoanInfoService.class,version = "0.0.1",timeout = 20000 )
    private LoanInfoService loanInfoService;

    @DubboReference(interfaceClass = BidInfoService.class,version = "0.0.1",timeout = 20000 )
    private BidInfoService bidInfoService;

    /**
     *首页访问控制层
     */
    @RequestMapping({"/","/index","index.html"})
    public ModelAndView index(ModelAndView mv){

        System.out.println("============index==============");

        //1. 历史年化收益率
        Double avgRate = loanInfoService.queryLoanInfoAvgRate();
        if (null != avgRate) {
            mv.addObject("avgRate", avgRate);
        }

        //2. 平台总人数、累计成交金额
        Long countAllUsers = userService.queryCountAllUsers();
        Double countAllBidMoney = bidInfoService.queryCountAllBidMoney();

        mv.addObject("countAllUsers", countAllUsers);
        mv.addObject("countAllBidMoney", countAllBidMoney);

    //产品展示
        Map<String, Object> parasMap = new HashMap<>();
        //3. 新手宝
        parasMap.put("type",0);
        parasMap.put("start", 0);
        parasMap.put("content",1);
        List<LoanInfo> loanInfosXsb = loanInfoService.queryLoanInfoByParasMap(parasMap);

        mv.addObject("loanInfos_xsb",loanInfosXsb);

        //4. 推荐（优先标）
        parasMap.put("type",1);
        parasMap.put("start", 0);
        parasMap.put("content",4);
        List<LoanInfo> loanInfosYxb = loanInfoService.queryLoanInfoByParasMap(parasMap);

        mv.addObject("loanInfos_yxb",loanInfosYxb);



        //5. 个人信用消费借款（散标）
        parasMap.put("type",2);
        parasMap.put("start", 0);
        parasMap.put("content",8);
        List<LoanInfo> loanInfosSb = loanInfoService.queryLoanInfoByParasMap(parasMap);

        mv.addObject("loanInfos_sb",loanInfosSb);

        //6. 视图
        mv.setViewName("index");
        return mv;
    }
}
