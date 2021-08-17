package com.scrh.money.web.controller;

import com.scrh.money.common.utils.Result;
import com.scrh.money.exterface.service.BidInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ScrH0052
 * @date 2021/8/11
 */
@RestController
public class BidInfoController {
    @DubboReference(interfaceClass = BidInfoService.class, version = "0.0.1", timeout = 20000)
    private BidInfoService bidInfoService;

    @PostMapping("/loan/bidInfo/invest")
    public Map<String, Object> invest(Long loanId, Double bidMoney, Long uid) {

        Map<String, Object> parasMap = new HashMap<>(3);
        parasMap.put("loanId", loanId);
        parasMap.put("bidMoney", bidMoney);
        parasMap.put("uid", uid);


        try {
            bidInfoService.invest(parasMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getError(e.getMessage());
        }

        return Result.getSuccess();

    }

}
