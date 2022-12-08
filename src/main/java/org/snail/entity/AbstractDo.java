package org.snail.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author yichu
 * @date 2021/4/21
 */
@Data
@Accessors(chain = true)
public class AbstractDo implements Serializable {

	private static final long serialVersionUID = -918298826981009545L;

	/**
     * 主键ID
     */
    protected Long id;

    /**
     * 创建人
     */
    protected Long creator;

    /**
     * 修改人
     */
    protected Long updator;

    /**
     * 标记删除位（0：正常；1：删除。），默认0
     */
    protected Integer mark;

    /**
     * 创建时间
     */
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    protected LocalDateTime updateTime;
}
