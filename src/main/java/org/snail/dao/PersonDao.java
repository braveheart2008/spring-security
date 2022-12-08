package org.snail.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.snail.entity.PersonDo;

@Mapper
public interface PersonDao {
	
	public PersonDo getByName(@Param("name") String name);

}
