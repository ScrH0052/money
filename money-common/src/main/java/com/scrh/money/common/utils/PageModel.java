package com.scrh.money.common.utils;

import lombok.Data;
import java.io.Serializable;
import java.util.Objects;

/**
 * 分页工具类
 * @author ScrH0052
 * @date 2021/8/3
 */
@Data
public class PageModel implements Serializable {
    /**
     * 数据库中开始的元素下标
     * @apiNote 默认值：0
     */
    private Integer start = 0;

    /**
     * 每页最多显示条目数
     * @apiNote 默认值：10
     */
    private Integer pageSize = 9;

    /**
     * 目标页（查询时）/当前页（页面中）
     */
    private Integer targetPage ;

    /**
     * 总记录数
     */
    private Integer totalCount ;

    /**
     * 总页数
     */
    private Integer totalPages ;

    /**
     * 根据 `pageSize` 及 `totalCount` 计算总页数
     * @return 总页数
     */
    public Integer getTotalPages() {
        if (null == totalPages) {
            if (null == totalCount){
                this.totalPages = 0;
            }else if (totalCount % pageSize != 0) {
                this.totalPages = totalCount / pageSize + 1;
            } else {
                this.totalPages = totalCount / pageSize;
            }
        }
        return this.totalPages;
    }

    /**
     * 获取上一页，当当前页为首页时，返回-1
     * @return 上一页
     */
    public Integer getLastPage() {
        if (1 == targetPage){
            return -1;
        }
        return targetPage - 1;
    }

    /**
     * 获取下一页，当当前页为末页时，返回-1
     * @return 下一页
     */
    public Integer getNextPage() {
        if (Objects.equals(getTotalPages(), targetPage)){
            return -1;
        }
        return targetPage + 1;
    }
}
