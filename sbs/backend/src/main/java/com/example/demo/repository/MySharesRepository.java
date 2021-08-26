package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.MySharesModel;
import org.springframework.data.jpa.repository.Query;

public interface MySharesRepository<MysharesModel> extends JpaRepository<MySharesModel, Integer> {
	
	//List<MySharesModel> getUserShares(String email);
	
	

}
