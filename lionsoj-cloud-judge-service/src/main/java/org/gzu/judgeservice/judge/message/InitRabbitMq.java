package org.gzu.judgeservice.judge.message;

import org.gzu.common.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname: InitRabbitMq
 * @Description: 初始化消息队列
 * @Author: lions
 * @Datetime: 1/11/2024 2:59 AM
 */
@Configuration
public class InitRabbitMq {

    /**
     * @Description:
     * @Return: 创建广播交换机
     * @Author: lions
     * @Datetime: 1/12/2024 4:42 AM
     */
    @Bean(name = MQConstant.MQ_CODE_EXCHANGE)
    public FanoutExchange codeExchange() {
        return new FanoutExchange(MQConstant.MQ_CODE_EXCHANGE, true, false);
    }

    /**
     * @Description: 创建队列
     * @Return: code_queue1
     * @Author: lions
     * @Datetime: 1/12/2024 4:42 AM
     */
    @Bean(name = MQConstant.MQ_CODE_QUEUE1)
    public Queue CodeQueue1() {
        return new Queue(MQConstant.MQ_CODE_QUEUE1, true, false, false);
    }

    /**
     * @Description: 绑定队列到广播交换机
     * @param fanoutQueue1 队列
     * @param fanoutExchange 广播交换机
     * @Return: 绑定对象
     * @Author: lions
     * @Datetime: 1/12/2024 4:43 AM
     */
    @Bean
    public Binding bindingCodeQueue1(@Qualifier(MQConstant.MQ_CODE_QUEUE1) Queue fanoutQueue1,
                                       @Qualifier(MQConstant.MQ_CODE_EXCHANGE) FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
}
