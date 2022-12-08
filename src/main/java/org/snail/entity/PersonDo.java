package org.snail.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PersonDo extends AbstractDo{
	/**
     * （用户）名称
     */
	private String name;
	/**
     * （用户）密码
     */
	private String password;

}
