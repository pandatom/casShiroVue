package com.pandatom.example.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@TableName("tb_permissions")
public class Permissions extends Model<Permissions> {

	private static final long serialVersionUID = 1L;

	@TableId(value="permission_id", type= IdType.AUTO)
	private Integer permissionId;
	@TableField("permission_code")
	private String permissionCode;
	@TableField("permission_name")
	private String permissionName;


	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	protected Serializable pkVal() {
		return this.permissionId;
	}

	@Override
	public String toString() {
		return "Permissions{" +
		", permissionId=" + permissionId +
		", permissionCode=" + permissionCode +
		", permissionName=" + permissionName +
		"}";
	}
}
