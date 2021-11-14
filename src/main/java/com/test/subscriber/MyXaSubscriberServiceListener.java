package com.test.subscriber;

import com.guzt.starter.mq.pojo.MessageStatus;
import com.guzt.starter.mq.pojo.TopicMessage;
import com.guzt.starter.mq.pojo.XaTopicMessage;
import com.guzt.starter.mq.service.TopicListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 订阅者
 * 注意！！！！ 【类的名称不要写成 MySubscriber2Service，mySubscriber2Service是消息消费者的Bean name】
 *
 * @author guzt
 */
@Component
public class MyXaSubscriberServiceListener implements TopicListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getSubscriberBeanName() {
        // 注意！！！！ 【类的名称不要写成 MySubscriber2Service，mySubscriber2Service是消息消费者的Bean name】
        // 请确保和ymal文件中消息消费者中的 beanName 一致
        return "myXaSubscriberService";
    }

    @Override
    public String getTopicName() {
        // 订阅订单中心类的主题消息
        return "TOPIC-XA-TEST001";
    }

    @Override
    public String getTagExpression() {
        // * 表示所有  || 表示订阅多个tag，例如  TAG-A||TAG-B||TAG-C
        return "*";
    }

    @Override
    public MessageStatus subscribe(TopicMessage topicMessage) {
        // 本地事务执行器唯一编号
        String localTransactionExecuterId = topicMessage.getUserProperties().getProperty(XaTopicMessage.LOCALTRANSACTION_EXECUTERID_KEY);
        logger.info("【分布式事务消息】MyXaSubscriberServiceListener 消费消息 message body = {}, 本地事务执行器唯一编号= {}",
                new String(topicMessage.getMessageBody(), StandardCharsets.UTF_8), localTransactionExecuterId);
        String testTag = "consumRetry";
        if (testTag.equals(topicMessage.getTags())) {
            // 模拟失败场景
            return MessageStatus.ReconsumeLater;
        } else {
            return MessageStatus.CommitMessage;
        }
    }
}
