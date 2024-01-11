package org.gzu.common.common;

import lombok.Data;
import org.gzu.common.constant.CommonConstant;

/**
 * @Classname: PageRequest
 * @Description: 分页请求封装
 * @Author: lions
 * @Datetime: 12/29/2023 12:13 AM
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
