package org.gzu.sandboxservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname: HealthCheckController
 * @Description: 心跳检测 Http api
 * @Author: lions
 * @Datetime: 1/4/2024 11:43 PM
 */
@RestController()
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "ok";
    }
}
