package com.mua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2022-09-27 下午 4:08
 * @Comment: 邮件发送
 */
@Slf4j
@RestController
@RequestMapping("sendEmail")
public class SendEmail {

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 纯文本邮件测试
     */
        @PostMapping("sendMessage")
    public void sendMessage(String to, String subject, String content) {
        Long startTimes = System.currentTimeMillis();
        //调用emailUtil中的方法
        emailUtil.sendMessage(to, subject, content);
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }

    /**
     * html邮件测试
     */
    @PostMapping("sendHtmlMail")
    public void sendHtmlMail(String to, String subject, String content) {
        Long startTimes = System.currentTimeMillis();
        //调用emailUtil中的方法
        emailUtil.sendHtmlMail(to, subject, content);
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }

    /**
     * 带附件邮件测试(单文件)
     * 文件类型可选
     */
    @PostMapping("sendMessageCarryFile")
    public void sendMessageCarryFile(String to, String subject, String content, MultipartFile file) {
        Long startTimes = System.currentTimeMillis();
        //调用emailUtil中的方法
        emailUtil.sendMessageCarryFile(to, subject, content, file);
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }

    /**
     * 带附件邮件测试(多文件)
     * 文件类型可选
     */
    @PostMapping("sendMessageCarryFiles")
    public void sendMessageCarryFiles(String to, String subject, String content, MultipartFile[] files) {
        Long startTimes = System.currentTimeMillis();
        //调用emailUtil中的方法
        emailUtil.sendMessageCarryFiles(to, subject, content, files);
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }

    /**
     * 发送静态图片(单个)
     */
    @PostMapping("sendInlineMail")
    public void sendInlineMail(String to, String subject, String content, MultipartFile file) {
        Long startTimes = System.currentTimeMillis();
        //调用emailUtil中的方法
        emailUtil.sendInlineMail(to, subject, content, file);
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }

    /**
     * 邮件模板测试 读取模板替换content
     */
    @PostMapping("sendTemplate")
    public void sendTemplate(String to, String subject) {
        Long startTimes = System.currentTimeMillis();
        //随机数
        Random random = new Random();
        String code = String.valueOf(random.nextInt(9));
        for (int i = 0; i < 5; i++) {
            code += random.nextInt(9);
        }
        //调用emailUtil中的方法
        emailUtil.sendHtmlMail(to, subject, emailUtil.buildContent(code));
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }

    /**
     * 邮件模板测试 (使用template)
     */
    @PostMapping("sendTemplate1")
    public void sendTemplate1(String to, String subject) {
        Long startTimes = System.currentTimeMillis();
        //随机数
        Random random = new Random();
        String code = String.valueOf(random.nextInt(9));
        for (int i = 0; i < 5; i++) {
            code += random.nextInt(9);
        }

        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", to);
        dataMap.put("code", code);
        dataMap.put("createTime", date);

        //调用emailUtil中的方法
        emailUtil.sendTemplate(to, subject, dataMap);
        Long endTimes = System.currentTimeMillis();
        log.info("发送成功耗时:" + (endTimes - startTimes) / 1000.0 + "s");
    }
}
