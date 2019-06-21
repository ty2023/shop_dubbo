package com.yj.constant;

/**
 * @author 29029
 * @version 1.0
 * @time 9:43
 */
public interface RabbitMqConstant {
    public final static String PRODUCT_FANOUT_EXCHANGE ="product_Exchange";

    public final static String PRODUCT_FANOUT_DEL_EXCHANGE ="product_Del_Exchange";

    public final static String USER_EXCHANGE ="user_Exchange";

    public final static String ITEM_QUEUE ="item_Queue";

    public final static String ITEM_DEL_QUEUE ="item_Del_Queue";

    public final static String SEARCH_QUEUE ="search_Queue";

    public final static String SEARCH_DEL_QUEUE ="search_Del_Queue";

    public final static String USER_EMAIL ="email_Queue";
}
