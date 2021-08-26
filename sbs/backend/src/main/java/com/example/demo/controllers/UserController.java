package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.UserModel;
import com.example.demo.repository.UserRepository;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/signin")
	public Map<String, String>  login(@RequestBody Map<String, Object> payload)
	{
		HashMap<String, String> map = new HashMap<>();
		

		try 
		{  
			//String usermailid=(String) payload.get("email");
		    UserModel us=userRepository.getById((String) payload.get("email"));
		
			
					String payloadPassword=(String) payload.get("password");
					String password=us.getPassword();
					if(BCrypt.checkpw(payloadPassword,password))
					//if(us.getPassword().equals(payload.get("password")))
					
					{
						map.put("status", "success");
						return map;
					}
					else
					{
						map.put("status", "failure");
						return map;
					}
				
			
		}
		catch(Exception e)
		{
			map.put("status",  e.getMessage());
			return map;
		}
		//map.put("status",  "uncaught error");
		//return map;
		
	
	}
	
	
	@PostMapping("/register")
	public Map<String, String> register(@RequestBody Map<String, Object> payload) 
	{
		String email = (String) payload.get("email");
		long phone = Long.parseLong((String) payload.get("phone"));
		String name = (String) payload.get("name");
		String password = (String) payload.get("password");
		 //BasicTextEncryptor bte = new BasicTextEncryptor();
		 //String encryptedpassword = bte.encrypt(password);
		String encryptedpassword =BCrypt.hashpw(password, BCrypt.gensalt());
	
		UserModel user = new UserModel(name,email,encryptedpassword,phone);
		userRepository.save(user);
		HashMap<String, String> map = new HashMap<>();
		map.put("status", "success");
		return map;
	}

	
	


	@GetMapping("/get-user")
	public List<UserModel> getOne(@RequestParam String email)
	{
		
		List<UserModel> user = new ArrayList<UserModel>();
		UserModel us = userRepository.getById(email);
		try 
		{  
					user.add(us);
		}
		catch(Exception e)		
		{	System.out.print("Exception : ");	
			System.out.print(e.getMessage());
		}
		return user;
	}
}
