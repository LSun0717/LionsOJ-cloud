package org.gzu.common.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname: DeleteRequest
 * @Description: 通用删除请求封装
 * @Author: lions
 * @Datetime: 12/29/2023 12:13 AM
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}