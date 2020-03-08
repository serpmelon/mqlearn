package com.togo.mq;

import org.springframework.amqp.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>mq exchange 枚举
 * 系统启动会根据枚举名称创建对应的exchange bean,
 * 枚举名称命名为A_B_C, 生成的bean名称为aBCExchange.
 * </p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author taiyn
 * @version 1.0
 * @date Created in 2019年12月09日 16:49
 * @since 1.0
 */

public enum ExchangeEnums {

    RENT_SIGN("rent.sign", ExchangeTypes.TOPIC, true, false),
    ;

    private static final String EXCHANGE = "Exchange";
    private static final String EXCHANGE_PREFIX = "crm.e.";

    private static Map<ExchangeEnums, Exchange> exchangeMap;

    private static Map<ExchangeEnums, Class<? extends Exchange>> classMap;

    static {

        exchangeMap = new HashMap<>();
        classMap = new HashMap<>();

        for (ExchangeEnums exchangeEnums : ExchangeEnums.values()) {

            switch (exchangeEnums.getExchangeType()) {

                case "direct": {

                    DirectExchange directExchange = new DirectExchange(exchangeEnums.getExchangeName(), exchangeEnums.isDurable(), false);
                    exchangeMap.put(exchangeEnums, directExchange);
                    classMap.put(exchangeEnums, directExchange.getClass());
                    break;
                }
                case "topic": {

                    TopicExchange topicExchange = new TopicExchange(exchangeEnums.getExchangeName(), exchangeEnums.isDurable(), false);
                    exchangeMap.put(exchangeEnums, topicExchange);
                    classMap.put(exchangeEnums, topicExchange.getClass());
                    break;
                }
                case "fanout": {

                    FanoutExchange fanoutExchange = new FanoutExchange(exchangeEnums.getExchangeName(), exchangeEnums.isDurable(), false);
                    exchangeMap.put(exchangeEnums, fanoutExchange);
                    classMap.put(exchangeEnums, fanoutExchange.getClass());
                    break;
                }
                case "headers": {

                    HeadersExchange headersExchange = new HeadersExchange(exchangeEnums.getExchangeName(), exchangeEnums.isDurable(), false);
                    exchangeMap.put(exchangeEnums, headersExchange);
                    classMap.put(exchangeEnums, headersExchange.getClass());
                    break;
                }
                default:
                    throw new RuntimeException();
            }
        }
    }

    /**
     * 自定义名字, 但是不是最后的exchange名字, 真正的名字需要加前缀
     */
    private String value;
    /**
     * 类型
     */
    private String exchangeType;
    /**
     * 持久化, true持久
     */
    private boolean durable;
    /**
     *
     */
    private boolean autoDelete;

    ExchangeEnums(String value, String exchangeType, boolean durable, boolean autoDelete) {

        this.value = value;
        this.exchangeType = exchangeType;
        this.durable = durable;
        this.autoDelete = autoDelete;
    }

    /**
     * exchange的真正名字, 前缀 + 定义的名称
     * @return
     */
    public String getExchangeName() {

        return EXCHANGE_PREFIX + value;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    /**
     * <pre>
     * desc : bean名称
     * A_B_C, 生成的bean名称为aBCExchange.
     * @author : taiyn
     * date : 2019-12-10 14:29
     * @param : []
     * @return java.lang.String
     * </pre>
     */
    public String getBeanName() {

        String originalName = name();
        // 转小写
        originalName = originalName.toLowerCase();
        String[] array = originalName.split("_");
        if (array.length < 2)
            return originalName + EXCHANGE;

        StringBuilder beanName = new StringBuilder(array[0]);
        for (int i = 1; i < array.length; i++) {

            beanName.append(array[i].substring(0, 1).toUpperCase() + array[i].substring(1));
        }

        return beanName.toString() + EXCHANGE;
    }

    public Exchange getExchange() {

        return exchangeMap.get(this);
    }

    public Class<? extends Exchange> getExchangeClass() {

        return classMap.get(this);
    }

    @Override
    public String toString() {
        return "ExchangeEnums{" +
                "value='" + value + '\'' +
                ", exchangeType='" + exchangeType + '\'' +
                ", durable=" + durable +
                ", autoDelete=" + autoDelete +
                '}';
    }
}