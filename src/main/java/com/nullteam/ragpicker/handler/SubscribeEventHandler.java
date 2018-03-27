package com.nullteam.ragpicker.handler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Title: SubscribeEventHandler.java</p>
 * <p>Package: com.nullteam.ragpicker.handler</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 03/27/18
 * @author WangJie <i@i8e.net>
 */
@Component
public class SubscribeEventHandler implements WxMpMessageHandler {

    private static final String SUBSCRIBE_EVENT_MESSAGE = "welcome messages"; // TODO: complete welcome msg

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        return WxMpXmlOutMessage.TEXT()
                .content(SUBSCRIBE_EVENT_MESSAGE)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
