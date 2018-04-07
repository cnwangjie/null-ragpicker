package com.nullteam.ragpicker.handler;

import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.Order;
import com.nullteam.ragpicker.service.OrderService;
import com.nullteam.ragpicker.service.WxUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: CurrentOrderBtnClickEventHandler.java</p>
 * <p>Package: com.nullteam.ragpicker.handler</p>
 * <p>Description: 当前订单按钮点击事件处理器</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/15/18
 * @author WangJie <i@i8e.net>
 */
@Component
public class CurrentOrderBtnClickEventHandler implements WxMpMessageHandler {

    private final WxUserService wxUserService;

    private final OrderService orderService;

    private static final String NOT_COLLECTOR_REPLY = "您不是回收员";
    private static final String NO_ORDER_REPLY = "当前没有需要您回收的订单";
    private static final String REPLY_TEMPLATE = "尊敬的回收员%s：\n当前有 %d 件订单需要由您回收，最近一件的信息如下\n\n" +
            "------------\n下单时间 %s\n地址 %s\n物品清单 \n%s\n------------\n\n<a href=\"%s\">点击查看全部详情</a>";
    private static final String DATE_FORMAT_TEMPLATE = "MM月dd日 HH:mm (E)";

    @Autowired
    public CurrentOrderBtnClickEventHandler(WxUserService wxUserService,
                                            OrderService orderService) {
        this.wxUserService = wxUserService;
        this.orderService = orderService;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        String msg;
        String openid = wxMessage.getOpenId();
        Collector collector = wxUserService.getOneByWxId(openid).getCollector();
        do {
            if (collector == null) {
                msg = NOT_COLLECTOR_REPLY;
                break;
            }
            List<Order> orders = orderService.getAllottedOrdersByCollector(collector);
            if (orders.size() == 0) {
                msg = NO_ORDER_REPLY;
                break;
            }
            Order nearlyOrder = orders.get(0);
            String orderDetail = ""; // TODO: order detail formatter
            msg = String.format(REPLY_TEMPLATE,
                    collector.getName(),
                    orders.size(),
                    new SimpleDateFormat(DATE_FORMAT_TEMPLATE).format(nearlyOrder.getCreatedAt()),
                    nearlyOrder.getLocDetail(),
                    orderDetail,
                    ""); // TODO: OAuth link to /collector/order/allotted
        } while (false);

        return WxMpXmlOutMessage.TEXT()
                .content(msg)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
