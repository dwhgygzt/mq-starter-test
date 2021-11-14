package com.test.xa;


import com.guzt.starter.mq.pojo.LocalTransactionStatus;
import com.guzt.starter.mq.pojo.XaTopicMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 模拟本地支付业务逻辑
 *
 * @author guzt
 */
@Service
public class PayTestService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 记得引入 @Transactional
     */
    @SuppressWarnings("unchecked")
    public void createPayOrder(Object businessParam) {
        logger.info("创建了支付订单..., 相关参数= {}", businessParam);
        String commitFlag = "COMMIT";
        if (businessParam instanceof Map) {
            Object value = ((Map<String, Object>) businessParam).get("tag");
            if (!commitFlag.equals(value)) {
                throw new RuntimeException("模拟本地事务执行失败");
            }
        }
    }

    /**
     * 从你的业务逻辑判断 msg 本地事务是否已经执行成功？
     *
     * @param msg XaTopicMessage
     * @return LocalTransactionStatus
     */
    public LocalTransactionStatus checkOrder(XaTopicMessage msg) {
        String testStr1 = "UNKNOW";
        String testStr2 = "ROLLBACK";
        if (msg.getTags().equals(testStr1)) {
            logger.info("模拟最终查询事务回滚...");
            return LocalTransactionStatus.UNKNOW;
        } else if (msg.getTags().equals(testStr2)) {
            return LocalTransactionStatus.ROLLBACK;
        } else {
            logger.info("模拟最终查询事务提交...");
            return LocalTransactionStatus.COMMIT;
        }
    }


}
