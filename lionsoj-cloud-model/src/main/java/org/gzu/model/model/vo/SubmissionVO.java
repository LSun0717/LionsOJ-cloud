package org.gzu.model.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.gzu.model.model.dto.judge.JudgeInfo;
import org.gzu.model.model.entity.Submission;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname: SubmissionVO
 * @Description: 题目提交信息视图对象
 * @Author: lions
 * @Datetime: 12/28/2023 10:22 PM
 */
@Data
public class SubmissionVO implements Serializable {

    private final static Gson GSON = new Gson();

    /**
     * id
     */
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
     * 判题信息
     */
    private JudgeInfo judgeInfo;

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
     * 题目信息
     */
    private QuestionVO questionVO;

    /**
     * 答题人信息
     */
    private UserVO userVO;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * VO 转 POJO
     * @param submissionVO 题目提交信息试图对象
     * @return Submission obj
     */
    public static Submission voToObj(SubmissionVO submissionVO) {
        if (submissionVO == null) {
            return null;
        }
        Submission submission = new Submission();
        BeanUtils.copyProperties(submissionVO, submission);
        JudgeInfo judgeInfo = submissionVO.getJudgeInfo();
        if (judgeInfo != null) {
            submission.setJudgeInfo(GSON.toJson(judgeInfo));
        }
        return submission;
    }

    /**
     * POJO to VO
     * @param submission 题目提交信息POJO
     * @return SubmissionVO obj
     */
    public static SubmissionVO objToVo(Submission submission) {
        if (submission == null) {
            return null;
        }
        SubmissionVO submissionVO = new SubmissionVO();
        BeanUtils.copyProperties(submission, submissionVO);

        String judgeInfoStr = submission.getJudgeInfo();
        submissionVO.setJudgeInfo(GSON.fromJson(judgeInfoStr, new TypeToken<JudgeInfo>(){
        }.getType()));
        return submissionVO;
    }
}
