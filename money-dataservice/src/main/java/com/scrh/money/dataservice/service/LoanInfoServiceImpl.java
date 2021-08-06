package com.scrh.money.dataservice.service;

import com.scrh.money.common.utils.PageModel;
import com.scrh.money.dataservice.mapper.LoanInfoMapper;
import com.scrh.money.exterface.domain.LoanInfo;
import com.scrh.money.exterface.service.LoanInfoService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品信息服务业务实现层
 * @author ScrH0052
 * @date 2021/8/2
 */
@DubboService(interfaceClass = LoanInfoService.class,version = "0.0.1",timeout = 20000 )
@Component
@AllArgsConstructor
public class LoanInfoServiceImpl implements LoanInfoService {

    private RedisTemplate<Object,Object> redisTemplate;

    private LoanInfoMapper loanInfoMapper;

    /**
     * 查询历史年化收益率,长时间不变化，设置缓存时间为1天
     * @return 历史年化收益率
     */
    @Override
    public Double queryLoanInfoAvgRate() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Double loanHistoryAvgRate = (Double) redisTemplate.opsForValue().get("LoanHistoryAvgRate");
        if (null == loanHistoryAvgRate) {
            loanHistoryAvgRate = loanInfoMapper.selectLoanHistoryAvgRate();
            redisTemplate.opsForValue().set("loanHistoryAvgRate", loanHistoryAvgRate, Duration.ofDays(1));
        }
        return loanHistoryAvgRate;
    }

    /**
     *
     * @param parasMap 查询条件：type-种类，start-开始索引，content-查找条数
     * @return 符合查询条件的产品列表
     */
    @Override
    public List<LoanInfo> queryLoanInfoByParasMap(Map<String, Object> parasMap) {
        return loanInfoMapper.selectLoanInfoByParasMap(parasMap);
    }

    /**
     *
     * @param targetPage 目标页码
     * @param type 产品类型编码
     * @return 对应产品信息列表
     */
    @Override
    public Map<String,Object> queryLoanProductByTypeCode(Integer targetPage, Integer type) {
        Map<String,Object> result = new HashMap<>(10);
        PageModel pm = new PageModel();
        pm.setTargetPage(targetPage);
        //设置当前页属性
        Integer start = (pm.getTargetPage()-1)*pm.getPageSize();
        pm.setStart(start);
        //查询页面信息
        Integer totalCount = (Integer) redisTemplate.opsForValue().get("totalCountOf"+type);
        if (null == totalCount){
            totalCount = loanInfoMapper.selectCountOfLoanProductByTypeCode(type);
            redisTemplate.opsForValue().set("totalCountOf"+type, totalCount,Duration.ofMinutes(5));
        }
        pm.setTotalCount(totalCount);
        result.put("pageInfo", pm);

        //获取产品列表
        Map<String,Object> parasMap = new HashMap<>(10);
        parasMap.put("type",type);
        parasMap.put("start", pm.getStart());
        parasMap.put("content",pm.getPageSize());

        List<LoanInfo> loanInfoList = loanInfoMapper.selectLoanInfoByParasMap(parasMap);
        result.put("loanInfoList", loanInfoList);

        return result;
    }

    /**
     *
     * @param id 查询的产品ID
     * @return id对应产品的信息
     */
    @Override
    public LoanInfo queryLoanInfoById(Long id) {
        return loanInfoMapper.selectByPrimaryKey(id);
    }
}
