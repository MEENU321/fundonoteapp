package com.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.note.model.*;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

	public List<User> findByEmailAndPassword(String email, String password);

	public void deleteByUserId(int id);

	public Optional<User> findByUserId(int id);

	public List<User> findByEmail(String email);

}