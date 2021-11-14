package com.test.subscriber.retry;

import com.guzt.starter.mq.pojo.Message;
import com.guzt.starter.mq.pojo.TopicMessage;
import com.guzt.starter.mq.service.RetryConsumFailHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * MQ消费者,尝试了最大次数后失败时的处理者
 *
 * @author guzt
 */
@Component
public class MyRetryConsumFailHandler implements RetryConsumFailHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(Message message) {
        if (message instanceof TopicMessage) {
            TopicMessage topicMessage = (TopicMessage) message;
            logger.debug("MQ消费者,尝试了最大次数 {} 后失败时的处理方法，getBussinessKey={}",
                    topicMessage.getCurrentRetyConsumCount(), topicMessage.getBussinessKey());
        } else {
            logger.debug("MQ消费者,尝试了最大次数后失败时的处理方法，getMessageId={}", message.getMessageId());
        }
    }
}
