package org.snail.service.impl;

import java.util.List;

import org.snail.dao.PersonDao;
import org.snail.entity.PersonDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PersonDao personDao;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		PersonDo personDo = personDao.getByName(name);
		if(personDo == null){
			throw new UsernameNotFoundException("用户名不存在!");
		}
		
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("leader");
		User user = new User(personDo.getName(), personDo.getPassword(), authorities);
		
		return user;
	}

}
