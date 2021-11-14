package com.test.xa;

import com.guzt.starter.mq.pojo.LocalTransactionStatus;
import com.guzt.starter.mq.pojo.XaTopicMessage;
import com.guzt.starter.mq.service.XaTopicLocalTransactionExecuter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对事务消息，必须有一个本地事务执行器，为了执行本地事务和消息的回查
 *
 * @author guzt
 */
@Component
public class MyXaTopicLocalTransactionExecuter implements XaTopicLocalTransactionExecuter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 本地事务执行器的唯一编号
    public static String EXECUTER_ID = "BUSINESS_XA_EXECUTER_001";

    @Resource
    private PayTestService payTestService;

    @Override
    public String getLocalTransactionExecuterId() {
        return MyXaTopicLocalTransactionExecuter.EXECUTER_ID;
    }

    @Override
    public LocalTransactionStatus executeLocalTransaction(XaTopicMessage msg, Object businessParam) {

        // 执行你的数据库操作  mybaties 等
        try {
            payTestService.createPayOrder(businessParam);
            return LocalTransactionStatus.COMMIT;
        } catch (Exception e) {
            logger.error("本地事务方法异常，因此事务消息也回滚.", e);
            return LocalTransactionStatus.ROLLBACK;
        }
    }

    @Override
    public LocalTransactionStatus checkLocalTransaction(XaTopicMessage msg) {
        return payTestService.checkOrder(msg);
    }

}
