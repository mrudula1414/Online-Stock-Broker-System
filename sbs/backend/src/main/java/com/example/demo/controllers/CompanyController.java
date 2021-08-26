package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Company;
import com.example.demo.repository.CompanyRepository;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class CompanyController {

	
	@Autowired
	private CompanyRepository companyRepository;
	@GetMapping("/get-all")
	public List<Company> getAll()
	{
		List<Company> companyDetails = (List<Company>)companyRepository.findAll();
		return companyDetails;
	}
	
	@GetMapping("/get-one")
	public Optional <Company> getOne(@RequestParam int id)
	{
		return companyRepository.findById(id);
	}
}
