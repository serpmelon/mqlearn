package com.togo.mq;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author taiyn
 * @version 1.0
 * @date Created in 2019年12月11日 16:51
 * @since 1.0
 */
public enum QueueEnums {

    TEST_QUEUE("test", true, false, false),
    ;

    private static final String QUEUE = "Queue";
    private static final String QUEUE_PREFIX = "crm.q.";

    private String value;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;



    QueueEnums(String value, boolean durable, boolean exclusive, boolean autoDelete) {

        this.value = value;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public String getQueueName() {

        return QUEUE_PREFIX + value;
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
            return originalName + QUEUE;

        StringBuilder beanName = new StringBuilder(array[0]);
        for (int i = 1; i < array.length; i++) {

            beanName.append(array[i].substring(0, 1).toUpperCase() + array[i].substring(1));
        }

        return beanName.toString() + QUEUE;
    }

}