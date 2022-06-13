package com.pandatom.example.entity;


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
@TableName("tb_rps")
public class Rps extends Model<Rps> {

	private static final long serialVersionUID = 1L;

	private Integer rid;
	private Integer pid;


	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Rps{" +
		", rid=" + rid +
		", pid=" + pid +
		"}";
	}
}
