package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Company;
import com.example.demo.models.MySharesModel;
import com.example.demo.models.UserModel;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.MySharesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WatchListRepository;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class MySharesController {
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	

	
	@Autowired
	private MySharesRepository mySharesRepository;
	
	
	
	
	
	@GetMapping("/my-share")
	public List<MySharesModel> myShare(@RequestParam("email") String email)
	{
		List<MySharesModel> allShares = (List<MySharesModel>)mySharesRepository.findAll();
		List<MySharesModel> myShares = new ArrayList<MySharesModel>();
		try 
		{
			Iterator<MySharesModel> iter = allShares.iterator();
			while(iter.hasNext())
			{
				MySharesModel ms = (MySharesModel) iter.next();
				if(email.equals(ms.getUser_id()))
				{
					System.out.print(ms);
					myShares.add(ms);
				}
			}
		}
		catch(Exception e)		
		{	System.out.print("Exception : ");	
			System.out.print(e.getMessage());
		}
		
		return myShares;
	}
	@PostMapping("/buy")
	public Map<String, String> buy(@RequestBody Map<String, Object> payload) 
	{
		String email = (String) payload.get("email");
		int companyId = Integer.parseInt(payload.get("companyId").toString());
		int qty = Integer.parseInt(payload.get("quantity").toString());
		HashMap<String, String> map = new HashMap<>();

		List<UserModel> users = (List<UserModel>) userRepository.findAll();
		List<UserModel> user = new ArrayList<UserModel>();
		
		List<MySharesModel> allShares = (List<MySharesModel>)mySharesRepository.findAll();
		List<MySharesModel> myShares = new ArrayList<MySharesModel>();
		
		List<Company> allCompany = (List<Company>)companyRepository.findAll();
		List<Company> company = new ArrayList<Company>();
		
		
				
		try
		{
			Iterator<UserModel> iter = users.iterator();
			while(iter.hasNext())
			{
				UserModel us = (UserModel) iter.next();
				if(email.equals(us.getEmail()))
					user.add(us);				
			}
			
			Iterator<MySharesModel> iter2 = allShares.iterator();
			while(iter2.hasNext())
			{
				MySharesModel ms = (MySharesModel) iter2.next();
				if(email.equals(ms.getUser_id()))				
				{					
					if(ms.getCompany_id() == companyId)
						myShares.add(ms);
				}
			}
			
			Iterator<Company> iter3 = allCompany.iterator();
			while(iter3.hasNext())
			{
				Company cm = (Company) iter3.next();
				if(companyId == cm.getCompany_id())				
					company.add(cm);					
			}
			
			if(user.get(0).getAmount_left()<=(qty*company.get(0).getCurrent_rate()))
			{
				map.put("status", "insufficient balance");
				return map;
			}			
			else if(!myShares.isEmpty())
			{
				int userAmount = user.get(0).getAmount_left();
				int currentAmount = userAmount - qty*company.get(0).getCurrent_rate();
				
				int userQty = myShares.get(0).getQuantity();
				int currentQty = userQty + qty;
				int volume=company.get(0).getVolume()-qty;
				company.get(0).setVolume(volume);
				companyRepository.save(company.get(0));
				myShares.get(0).setQuantity(currentQty);
				myShares.get(0).setBought_rate(company.get(0).getCurrent_rate());
				mySharesRepository.save(myShares.get(0));
				user.get(0).setAmount_left(currentAmount);
				userRepository.save(user.get(0));
				
				map.put("status", "success");
				return map;
			}
			else
			{
				int userAmount = user.get(0).getAmount_left();
				int currentAmount = userAmount - qty*company.get(0).getCurrent_rate();
				
				user.get(0).setAmount_left(currentAmount);
				userRepository.save(user.get(0));
				
				MySharesModel msm = new MySharesModel(companyId,company.get(0).getName(),email,company.get(0).getOpen_rate(),company.get(0).getClose_rate(),company.get(0).getPeak_rate(),company.get(0).getLeast_rate(),company.get(0).getCurrent_rate(),qty,company.get(0).getCurrent_rate(),company.get(0).getYear_low(),company.get(0).getYear_high(), company.get(0).getMarket_cap(),company.get(0).getP_e_ratio(),company.get(0).getVolume());
				mySharesRepository.save(msm);
				int volume=company.get(0).getVolume()-qty;
				company.get(0).setVolume(volume);
				companyRepository.save(company.get(0));
			}
		}
		catch(Exception e)		
		{	
			map.put("status", e.getMessage());
			return map;
		}

		map.put("status", "success");
		return map;
	}
	@PostMapping("/sell")
	public Map<String, String> sell(@RequestBody Map<String, Object> payload)
	{
		String email = (String) payload.get("email");
		int companyId = Integer.parseInt(payload.get("companyId").toString());
		int qty = Integer.parseInt(payload.get("quantity").toString());
		HashMap<String, String> map = new HashMap<>();
    /* Instead of fetching of all users from database, fetch on the basis of email*/
		List<UserModel> users = (List<UserModel>) userRepository.findAll();
		List<UserModel> user = new ArrayList<UserModel>();
		
		List<MySharesModel> allShares = (List<MySharesModel>)mySharesRepository.findAll();
		List<MySharesModel> myShares = new ArrayList<MySharesModel>();
		
		List<Company> allCompany = (List<Company>)companyRepository.findAll();
		List<Company> company = new ArrayList<Company>();
		
		try
		{
			Iterator<UserModel> iter = users.iterator();
			while(iter.hasNext())
			{
				UserModel us = (UserModel) iter.next();
				if(email.equals(us.getEmail()))
					user.add(us);				
			}
			
			Iterator<MySharesModel> iter2 = allShares.iterator();
			while(iter2.hasNext())
			{
				MySharesModel ms = (MySharesModel) iter2.next();
				if(email.equals(ms.getUser_id()))				
				{					
					if(ms.getCompany_id() == companyId)
						myShares.add(ms);
				}
			}
			
			Iterator<Company> iter3 = allCompany.iterator();
			while(iter3.hasNext())
			{
				Company cm = (Company) iter3.next();
				if(companyId == cm.getCompany_id())				
					company.add(cm);					
			}
			
			int userAmount = user.get(0).getAmount_left();
			int currentAmount = userAmount + qty*company.get(0).getCurrent_rate();
			
			int userQty = myShares.get(0).getQuantity();
			int currentQty = userQty - qty;
			/*step 1: fetch the user based on userid
			 *     2:clone the previous order 
			 *     3: then create new sell record with version++; (set bid as sell)
			 *     4:  if(curr-quantity>0) create a new buy record with version++    (set bid as buy)*/
			if(currentQty == 0)
			{
				mySharesRepository.delete(myShares.get(0));				
			}
			else
			{
				myShares.get(0).setQuantity(currentQty);
				mySharesRepository.save(myShares.get(0));
			}
			user.get(0).setAmount_left(currentAmount);
			userRepository.save(user.get(0));
			
			map.put("status", "success");
			return map;
			
		}
		catch(Exception e)
		{
			map.put("status", e.getMessage());
			return map;
		}
	}

}
