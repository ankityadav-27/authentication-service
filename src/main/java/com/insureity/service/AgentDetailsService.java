package com.insureity.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.insureity.dao.AgentDAO;
import com.insureity.model.Agent;

/**Service class*/
@Service
public class AgentDetailsService implements UserDetailsService {
	@Autowired
	private AgentDAO agentDAO;

	/**
	 * use interface class...
	 * 
	 * @param String
	 * @return User 
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String uid) {
		
		try
		{
			Agent user = agentDAO.findById(uid).orElse(null);
			if(user != null) {
				user.getUname();
			}
			return new User(user.getUserid(), user.getUpassword(), new ArrayList<>());
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("UsernameNotFoundException");
		}
			
		
		
	}

}
