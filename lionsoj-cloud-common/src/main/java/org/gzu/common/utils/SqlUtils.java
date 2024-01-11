package org.gzu.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Classname: SqlUtils
 * @Description: SQL工具类
 * @Author: lions
 * @Datetime: 12/29/2023 12:33 AM
 */
public class SqlUtils {

    /**
     * @Description: 校验排序字段是否合法（防止 SQL 注入）
     * @param sortField 排序字段
     * @Return: 是否合法
     * @Author: lions
     * @Datetime: 12/29/2023 12:33 AM
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
