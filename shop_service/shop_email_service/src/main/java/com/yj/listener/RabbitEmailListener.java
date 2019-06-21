package com.yj.listener;

import com.yj.constant.RabbitMqConstant;
import com.yj.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author 29029
 * @version 1.0
 * @time 13:27
 */
@Component
public class RabbitEmailListener {

    @Value("${yujie.emailFrom}")
    private String emailFrom;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 模板路径
     */
    @Value("${template:templates/mail.vm}")
    private String templateFile;


    @RabbitListener(queues = RabbitMqConstant.USER_EMAIL)
    public void setEmailFrom(User user){
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


    public String Context(User user){
        String yu = "";
        yu += "尊敬的用户"+user.getUserName()+",请<a href='http://localhost:8084/user/UpUserFlag/"+user.getId()+"'>点击激活账号</a>";
        yu +="（<span style='color: red'>激活成功就会进入登录页，" +
                "没激活成功，您的信息将会清除请检查，您注册的信息而且会返回主任</span>）";
        return  yu;
    }

}
