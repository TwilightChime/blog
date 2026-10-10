package com.twilightchime.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    @Async
    public void sendWelcomeEmail(String email, String username) {
        log.info("发送欢迎邮件: email={}, username={}", email, username);
    }
}
