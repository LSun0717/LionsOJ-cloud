package org.gzu.model.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname: Submission
 * @Description: 题目提交实体
 * @Author: lions
 * @Datetime: 12/28/2023 10:46 PM
 */
@TableName(value ="submission")
@Data
public class Submission implements Serializable {
    /**
     * id，雪花算法生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 代码语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息（json）
     */
    private String judgeInfo;

    /**
     * 判题状态（0-待判题、1-判题中、2-成功、3-失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}