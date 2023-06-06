package com.mua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: ASUS 徐伟
 * @Data: 2022/7/19 9:46
 * @Comment: 邮件发送工具类
 */
@Slf4j
@Component
public class EmailUtil {

    @Value("${spring.mail.from}") // 从application.yml配置文件中获取
    private String from; // 发送发邮箱地址

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;
    /**
     * 发送纯文本邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMessage(String to, String subject, String content) {
        // 创建一个邮件对象
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from); // 设置发送发
        msg.setTo(to); // 设置接收方
        msg.setSubject(subject); // 设置邮件主题
        msg.setText(content); // 设置邮件内容
        // 发送邮件
        mailSender.send(msg);
    }

    /**
     * 发送Html邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content 邮件内容（html格式）
     */
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            System.out.println("html邮件发送成功!");
        } catch (MessagingException e) {
            System.out.println("发送html邮件时发生异常！" + e);
            e.printStackTrace();
        }
    }

    /**
     * 发送带附件的邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content 邮件内容（html格式）
     * @param file    单个文件(文件路径)
     */
    public void sendMessageCarryFile(String to, String subject, String content, MultipartFile file) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from); // 设置发送发
            helper.setTo(to); // 设置接收方
            helper.setSubject(subject); // 设置邮件主题
            helper.setText(content, true); // 设置邮件内容
            helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file); // 单个附件
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 发送邮件
        mailSender.send(mimeMessage);
    }

    /**
     * 发送带附件的邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param content 邮件内容（发送内容）
     * @param files   文件数组 // 可发送多个附件
     */
    public void sendMessageCarryFiles(String to, String subject, String content, MultipartFile[] files) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from); // 设置发送发方
            helper.setTo(to); // 设置接收方
            helper.setSubject(subject); // 设置邮件主题
            helper.setText(content, true); // 设置邮件内容
            if (files != null && files.length > 0) { // 添加附件（多个）
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }
        } catch (MessagingException e) {
            System.out.println("发送失败" + e);
            e.printStackTrace();
        }
        // 发送邮件
        mailSender.send(mimeMessage);
    }

    /**
     * 发送单个静态图片
     *
     * @param to        接收方
     * @param subject   邮件主题
     * @param content   邮件内容（发送内容）
     * @param multiFile 图片
     */
    public void sendInlineMail(String to, String subject, String content, MultipartFile multiFile) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
            helper.setText("<html><body>带静态资源的邮件内容 图片:<img src='cid:picture' /></body></html>", true);

            /**
             * 将multiFile类型转为File类型
             */
            // 获取文件名
            String fileName = multiFile.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);

            // FileSystemResource file = new FileSystemResource(new File("C:\Users\ASUS\OneDrive\图片\Saved Pictures\bg1.png"));
            // FileSystemResource file = new FileSystemResource("C:\\Users\\ASUS\\OneDrive\\图片\\Saved Pictures\\bg1.png");

            helper.addInline("picture", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    /**
     * @param title 验证码
     * @return 读取邮件模板 替换模板中的信息
     */
    public String buildContent(String title) {
        //加载邮件html模板
        Resource resource = new ClassPathResource("templates/mailtemplate.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败----->{}", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }

    /**
     * 发送Html邮件信息
     *
     * @param to      接收方
     * @param subject 邮件主题
     * @param dataMap 模板内的数据
     * 邮件内容（读取模板 使用template）
     */
    public void sendTemplate(String to, String subject, Map<String, Object> dataMap) {

        //给模板引擎提供变量
        Context context = new Context();
        //设置传入模板页面的值   从dataMap中获取
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());//例如:context.setVariable("code",123)
        }
        //模板名称
        String emailTemplate = "mailtemplate1";
        String templateContent = templateEngine.process(emailTemplate, context);


        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(templateContent, true);

            mailSender.send(message);
            System.out.println("html邮件发送成功!");
        } catch (MessagingException e) {
            System.out.println("发送html邮件时发生异常！" + e);
            e.printStackTrace();
        }
    }
}
