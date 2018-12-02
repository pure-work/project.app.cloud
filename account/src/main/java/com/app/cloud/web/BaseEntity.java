package com.app.cloud.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - 基类
 */
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -67188388306700736L;
	/** "ID"属性名称 */
	public static final String ID_PROPERTY_NAME = "id";
	/** "创建时间"属性名称 */
	public static final String CREATE_DATE_PROPERTY_NAME = "createTime";
	/** "修改时间"属性名称 */
	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyTime";
	/** ID */
	@JsonProperty
	@Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="JDBC")
	@ApiModelProperty(value="主键", required = true)
	private Long id;

	/** 创建时间 */
	@JsonProperty
	// @Field(store = Store.YES, index = Index.UN_TOKENIZED)
	// @DateBridge(resolution = Resolution.SECOND)
	@Column(nullable = false, updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value="创建时间", required = true)
	private Date createTime;

	/** 修改时间 */
	@JsonProperty
	// @Field(store = Store.YES, index = Index.UN_TOKENIZED)
	// @DateBridge(resolution = Resolution.SECOND)
	@Column(nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value="修改时间", required = true)
	private Date modifyTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	@ApiModelProperty(value="页码", required = true)
	private Integer pageNo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	@ApiModelProperty(value="页数", required = true)
	private Integer pageSize;

	/**
	 * 重写equals方法
	 * @param obj 对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (this == obj) { return true; }
		if (!BaseEntity.class.isAssignableFrom(obj.getClass())) { return false; }
		BaseEntity other = (BaseEntity) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	/**
	 * 重写hashCode方法
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

	/**
	 * 插入调用
	 */
	public void persist() {
	}

	/**
	 * 更新调用
	 */
	public void update() {
	}

}