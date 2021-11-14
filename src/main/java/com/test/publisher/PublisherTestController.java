package com.test.publisher;

import com.guzt.starter.mq.exception.TopicMqException;
import com.guzt.starter.mq.pojo.TopicMessage;
import com.guzt.starter.mq.pojo.TopicMessageSendResult;
import com.guzt.starter.mq.pojo.XaTopicMessage;
import com.guzt.starter.mq.service.TopicPublisher;
import com.guzt.starter.mq.service.TopicSendCallback;
import com.guzt.starter.mq.service.XaTopicPublisher;
import com.test.xa.MyXaTopicLocalTransactionExecuter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息发送测试
 *
 * @author guzt
 * @since 2021-07-31 12:25:33
 */
@Api(tags = "【MQ】消息发送测试")
@RestController
@RequestMapping("mq")
public class PublisherTestController {

    private static Logger logger = LoggerFactory.getLogger(PublisherTestController.class);

    @Lazy
    @Resource(name = "myPublisherService")
    TopicPublisher myPublisherService;

    @Lazy
    @Resource(name = "myXaPublisherService")
    XaTopicPublisher myXaPublisherService;

    @ApiOperation(value = "异步发送普通消息")
    @PostMapping("publishAsync")
    public String publishAsync(String text, String tag) {
        TopicMessage msg = new TopicMessage();
        msg.setBussinessKey(System.currentTimeMillis() + "");
        msg.setTags(tag);
        msg.setTopicName("TOPIC-TEST001");
        msg.setMessageBody(text.getBytes());
        myPublisherService.publishAsync(msg, new TopicSendCallback() {
            @Override
            public void onSuccess(TopicMessageSendResult topicMessageSendResult) {
                logger.info("消息异步发送成功！ messageId = {}", topicMessageSendResult.getMessageId());
            }

            @Override
            public void onFail(TopicMqException topicMqException) {
                logger.error("消息异步发送失败！ messageId = {}", topicMqException.getMessageId());
            }
        });

        return "消息异步发送成功: " + System.currentTimeMillis();
    }

    @ApiOperation(value = "同步发送普通消息")
    @PostMapping("publish")
    public String publish(String text, String tag) {
        TopicMessage msg = new TopicMessage();
        msg.setBussinessKey(System.currentTimeMillis() + "");
        msg.setTags(tag);
        msg.setTopicName("TOPIC-TEST001");
        msg.setMessageBody(text.getBytes());
        myPublisherService.publish(msg);

        return "消息同步发送成功: " + System.currentTimeMillis();
    }

    @ApiOperation(value = "发送XA分布式事务消息")
    @PostMapping("publishXa")
    public String publishXa(String text, String tag) {
        XaTopicMessage msg = new XaTopicMessage();
        msg.setBussinessKey(System.currentTimeMillis() + "");
        msg.setTags(tag);
        msg.setTopicName("TOPIC-XA-TEST001");
        msg.setMessageBody(text.getBytes());
        // 本地事务执行器的唯一编号
        msg.setLocalTransactionExecuterId(MyXaTopicLocalTransactionExecuter.EXECUTER_ID);

        Map<String, Object> businessParam = new HashMap<>(8);
        businessParam.put("tag", tag);
        businessParam.put("message", text);
        myXaPublisherService.publishInTransaction(msg, businessParam);

        return "【分布式事务消息】发送XA分布式事务消息成功: " + System.currentTimeMillis();
    }


}