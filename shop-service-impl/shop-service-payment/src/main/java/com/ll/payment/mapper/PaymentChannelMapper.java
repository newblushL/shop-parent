package com.ll.payment.mapper;

import com.ll.payment.mapper.entity.PaymentChannelEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: PaymentChannelMapper
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Repository
public interface PaymentChannelMapper {

    @Select("SELECT channel_Name  AS channelName , channel_Id AS channelId, " +
            "merchant_Id AS merchantId,sync_Url AS syncUrl, asyn_Url AS asynUrl," +
            "public_Key AS publicKey, private_Key AS privateKey,channel_State AS channelState ," +
            "class_ADDRES as classAddres  FROM payment_channel WHERE CHANNEL_STATE='0';")
    List<PaymentChannelEntity> selectAll();

    @Select("SELECT channel_Name  AS channelName , channel_Id AS channelId, merchant_Id " +
            "AS merchantId,sync_Url AS syncUrl, asyn_Url AS asynUrl,public_Key AS publicKey, " +
            "private_Key AS privateKey,channel_State AS channelState ," +
            "class_ADDRES as classAddres  FROM payment_channel WHERE CHANNEL_STATE='0' " +
            " AND channel_Id=#{channelId} ;")
    PaymentChannelEntity selectBychannelId(String channelId);
}
