package com.app.api.user.entity;

import com.app.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_account")
@ApiModel(value = "用户账户")
@Getter
@Setter
public class UserAccount extends BaseEntity {
    private static final long serialVersionUID = 934278149340893196L;

    @ApiModelProperty(value="用户ID", required = true)
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty(value="用户名", required = true)
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty(value="用户手机", required = true)
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty(value="密码", required = true)
    @Column(name = "password")
    private String password;

    @ApiModelProperty(value="状态 1.正常 2.冻结", required = true)
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value="出生日期", required = true)
    @Column(name = "birthday")
    private Date birthday;

    @ApiModelProperty(value="年龄", required = true)
    @Column(name = "age")
    private Integer age;

    @ApiModelProperty(value="性别(1:男 2:女)", required = true)
    @Column(name = "sex")
    private Integer sex;

    @ApiModelProperty(value="姓名", required = true)
    @Column(name = "real_name")
    private String realName;

    @ApiModelProperty(value="身份证号码", required = true)
    @Column(name = "id_card")
    private String idCard;

    @ApiModelProperty(value="备注", required = true)
    @Column(name = "remark")
    private String remark;

}
