package com.insureity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insureity.dao.AgentDAO;
import com.insureity.model.Agent;
import com.insureity.model.AuthResponse;
import com.insureity.service.AgentDetailsService;
import com.insureity.service.JwtUtil;

@RequestMapping("/api")
@RestController
public class AuthenticationController {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private AgentDetailsService custdetailservice;
	@Autowired
	private AgentDAO userservice;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestBody Agent userlogincredentials) {
		//Generates token for login
		final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUserid());
		String uid = "";
		String generateToken = "";
		if (userdetails.getPassword().equals(userlogincredentials.getUpassword())  ) {
			uid = userlogincredentials.getUserid();
			generateToken = jwtutil.generateToken(userdetails);
			return new ResponseEntity<>(new Agent(uid,null, null, generateToken), HttpStatus.OK);
		} else {
			LOGGER.info("At Login : ");
			LOGGER.error("Invalid Login Credentials!!!");
			return new ResponseEntity<>("Invalid Login Credentials!!!", HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(value = "/validate")
	public ResponseEntity<Object> getValidity(@RequestHeader("Authorization") final String token) {
		
		//Returns response after Validating received token
		
		String token1 = token.substring(7);
		AuthResponse res = new AuthResponse();
		if (jwtutil.validateToken(token1)) {
			res.setUid(jwtutil.extractUsername(token1));
			res.setValid(true);
			res.setName(userservice.findById(jwtutil.extractUsername(token1)).get().getUname());
		} else {
			res.setValid(false);
			LOGGER.info("At Validity : ");
			LOGGER.error("Token Has Expired");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
