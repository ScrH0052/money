package com.scrh.money.dataservice.service;

import com.scrh.money.dataservice.mapper.BidInfoMapper;
import com.scrh.money.exterface.service.BidInfoService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * 投资记录表业务层
 * @author ScrH0052
 * @date 2021/8/2
 */
@DubboService(interfaceClass = BidInfoService.class,version = "0.0.1",timeout = 20000)
@Component
@AllArgsConstructor
public class BidInfoServiceImpl implements BidInfoService {

    private RedisTemplate<Object,Object> redisTemplate;

    private BidInfoMapper bidInfoMapper;


    @Override
    public Double queryCountAllBidMoney() {
        Double countAllBidMoney = (Double) redisTemplate.opsForValue().get("countAllBidMoney");
        if (null == countAllBidMoney) {
            countAllBidMoney = bidInfoMapper.selectCountAllBidMoney();
            redisTemplate.opsForValue().set("countAllBidMoney",countAllBidMoney, Duration.ofMinutes(5));
        }
        return countAllBidMoney;
    }
}
