package org.gzu.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @Classname: JsonConfig
 * @Description: Spring MVC Json 配置
 * @Author: lions
 * @Datetime: 12/29/2023 12:20 AM
 */
@JsonComponent
public class JsonConfig {

    /**
     * @Description: 添加 Long 转 json 精度丢失的配置
     * @param builder json转换构造器
     * @Return: 对象映射器
     * @Author: lions
     * @Datetime: 12/29/2023 12:21 AM
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);
        return objectMapper;
    }
}