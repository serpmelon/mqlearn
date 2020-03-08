package com.togo.mq;

import lombok.Getter;

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
 * @date Created in 2019年12月10日 14:17
 * @since 1.0
 */
@Getter
public enum RoutingKeyEnums {

    RESERVE_TO_SIGN("reserve.sign"),
    CRM_RENT_CONTRACT("crm.rent.contract"),
    RPOPERTY_CONFIRM("rent.sign.property.confirm"),
    PROPERTY_NEW_CONFIRM("rent.newsign.confirmProperty"),
    SMART_HOME("sign.contract.smart.home"),
    CRM_EVALUATE("crm.rent.contract.evaluate"),

    SIGN_CONFIRM_CONTRACT("rent.sign.contract.confirm"),
    ;

    private String value;

    RoutingKeyEnums(String value) {

        this.value = value;
    }

    @Override
    public String toString() {
        return "RoutingKeyEnums{" +
                "value='" + value + '\'' +
                '}';
    }
}