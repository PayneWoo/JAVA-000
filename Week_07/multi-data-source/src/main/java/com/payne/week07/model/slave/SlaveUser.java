package com.payne.week07.model.slave;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Payne
 * @date 2020/12/4
 */
@Data
@TableName("user_info")
public class SlaveUser {

    private int id;
    private String userId;
    private String userName;
    private String userSex;
    private Long userBirthdate;
    private int userAge;
    private String headUrl;
    private String buyerId;
    private String sellerId;
    private String userPhone;
    private String userAddress;
    private String alternateAddress;
    private String payAccount;
    private String receiveAccout;
    private BigDecimal payTotal;
    private int userLevel;
    private int userStatus;
    private int loginWay;
    private Long registerTime;
    private Long updateTime;
}
