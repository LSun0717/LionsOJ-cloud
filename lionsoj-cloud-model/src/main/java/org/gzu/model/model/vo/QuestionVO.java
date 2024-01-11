package org.gzu.model.model.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.gzu.model.model.dto.question.JudgeConfig;
import org.gzu.model.model.entity.Question;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Classname: QuestionVO
 * @Description: 题目视图对象
 * @Author: lions
 * @Datetime: 12/28/2023 10:19 PM
 */
@Data
public class QuestionVO implements Serializable {

    private final static Gson GSON = new Gson();

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目解析
     */
    private String answerBlog;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 测试配置（json数组）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

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

    private static final long serialVersionUID = 1L;

    /**
     * 创建题目人VO
     */
    private UserVO userVO;

    /**
     * @Description: VO to POJO
     * @param questionVO 题目VO
     * @Return: 题目POJO
     * @Author: lions
     * @Datetime: 12/28/2023 10:17 PM
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(GSON.toJson(tagList));
        }

        JudgeConfig judgeConfig = questionVO.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        return question;
    }

    /**
     * @Description: POJO to VO
     * @param question 题目POJO
     * @Return: 题目VO
     * @Author: lions
     * @Datetime: 12/28/2023 10:18 PM
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setTags(GSON.fromJson(question.getTags(), new TypeToken<List<String>>() {
        }.getType()));
        questionVO.setJudgeConfig(GSON.fromJson(question.getJudgeConfig(), new TypeToken<JudgeConfig>(){
        }.getType()));
        return questionVO;
    }
}
