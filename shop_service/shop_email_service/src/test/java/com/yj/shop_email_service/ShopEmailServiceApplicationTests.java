package com.yj.shop_email_service;

import com.yj.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopEmailServiceApplicationTests {

    @Value("${yujie.emailFrom}")
    private String emailFrom;

    @Autowired
    private JavaMailSender javaMailSender;


    @Test
    public void contextLoads() {
        User user = new User();
        user.setId(2L);
        user.setUserName("yujie");
        user.setEmail("2902992419@qq.com");
        System.out.println(user);
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人邮箱
            helper.setFrom(emailFrom);
            // 设置收件人邮箱
            helper.setTo(user.getEmail());
            // 设置邮件标题
            helper.setSubject("激活账号");
            String content = Context(user);
            helper.setText(content, true);
            // 发送邮件
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String Context(User user) {
        String yu = "尊敬的用户" + user.getUserName() + ",请<a href='http://localhost:8084/user/UpUserFlag/" + user.getId() + "'>点击激活账号</a>";
        return yu;
    }

}
