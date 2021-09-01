package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.MySharesModel;
import com.example.demo.models.UserActivity;
import com.example.demo.models.UserModel;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.MySharesRepository;
import com.example.demo.repository.UserActivityRepository;
import com.example.demo.repository.UserRepository;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	

	
	@Autowired
	private CompanyRepository companyRepository;
	

	
	@Autowired
	private UserActivityRepository userActivityRepository;

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
	public LocalDateTime getDateAndTime()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		   LocalDateTime now = LocalDateTime.now();
		  return now;
	}
	@PutMapping("/addfunds")
	public Map<String, String> addfunds(@RequestParam String email, @RequestBody Map<String, Object> payload)
    {
		HashMap<String, String> map = new HashMap<>();
		try {  
			int funds = Integer.parseInt((String) payload.get("amount"));
			UserModel us = userRepository.getById(email);
			
			int updatedfunds=us.getAmount_left()+funds;
			us.setAmount_left(updatedfunds);
			
			userRepository.save(us);
			
			UserActivity u1=new UserActivity();
			
			u1.setItem("Funds");
			u1.setRemarks(" You added "+funds+" to your account");
			u1.setTime(getDateAndTime());
			u1.setUserid(email);
			u1.setAction("Credit");
			userActivityRepository.save(u1);
			map.put("status", "success");
			return map;
        
		}catch(Exception e){		
			System.out.print(e.getMessage());
			map.put("status",  e.getMessage());
			return map;
		}
		
	}	
	@GetMapping("/myactivity")
	public List<UserActivity> myShare(@RequestParam("email") String email)
	{
		List<UserActivity> allActivities = (List<UserActivity>)userActivityRepository.findAll();
		List<UserActivity> myActivity = new ArrayList<UserActivity>();
		try 
		{
			Iterator<UserActivity> iter = allActivities.iterator();
			while(iter.hasNext())
			{
				UserActivity ms = (UserActivity) iter.next();
				if(email.equals(ms.getUserid()))
				{
					System.out.print(ms);
					myActivity.add(ms);
					
				}
			}
		}
		catch(Exception e)		
		{	System.out.print("Exception : ");	
			System.out.print(e.getMessage());
		}
		
		return myActivity;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
