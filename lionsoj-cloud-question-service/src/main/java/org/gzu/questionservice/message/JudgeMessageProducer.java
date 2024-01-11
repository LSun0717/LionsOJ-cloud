package org.gzu.questionservice.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname: JudgeMessageProducer
 * @Description: 消息生产者
 * @Author: lions
 * @Datetime: 1/11/2024 3:17 AM
 */
@Slf4j
@Component
public class JudgeMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * @Description: 发送MQ消息
     * @param exchangeName 交换机名称
     * @param routineKey 路由键
     * @param msg 消息
     * @Author: lions
     * @Datetime: 1/12/2024 4:56 AM
     */
    public void send(String exchangeName, String routineKey, String msg) {
        rabbitTemplate.convertAndSend(exchangeName, routineKey, msg);
    }
}
