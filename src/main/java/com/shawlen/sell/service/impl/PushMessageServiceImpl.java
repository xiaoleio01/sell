package com.shawlen.sell.service.impl;

import com.shawlen.sell.config.WechatAccountConfig;
import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author ShawLen
 * @date 2019/11/20 2:08 下午
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","亲，请记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","13720882594"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","¥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临！")
                );
        wxMpTemplateMessage.setData(dataList);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信消息模版】发送失败，{}",e);
        }
    }
}
