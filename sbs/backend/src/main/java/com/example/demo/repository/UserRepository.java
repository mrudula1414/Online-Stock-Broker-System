package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel,String>{

}
