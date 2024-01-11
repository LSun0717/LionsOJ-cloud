package org.gzu.judgeservice.judge.message;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.gzu.common.constant.MQConstant;
import org.gzu.judgeservice.judge.service.JudgeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname: CodeMessageConsumer
 * @Description: 消息队列消费者
 * @Author: lions
 * @Datetime: 1/11/2024 3:21 AM
 */
@Component
@Slf4j
public class CodeMessageConsumer {

    @Resource
    private JudgeService judgeService;

    /**
     * @Description: MQ监听器（手动确认消息收到，sandbox内部异常会进行requeue）
     * @param msg 消息
     * @param channel MQ channel
     * @param deliveryTag 投递消息id
     * @Author: lions
     * @Datetime: 1/12/2024 4:40 AM
     */
    @SneakyThrows
    @RabbitListener(queues = {MQConstant.MQ_CODE_QUEUE1}, ackMode = "MANUAL")
    public void receiveMsg(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("received message = {}", msg);
        try {
            long submissionId = Long.parseLong(msg);
            judgeService.doJudge(submissionId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
