package com.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.note.model.*;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
	public List<UserDetails> findByEmailAndPassword(String email, String password);

	public void deleteById(int id);

	public Optional<UserDetails> findById(int id);

	public List<UserDetails> findByEmail(String email);

}