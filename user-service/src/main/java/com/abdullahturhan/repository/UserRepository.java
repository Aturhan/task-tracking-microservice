package com.abdullahturhan.repository;

import com.abdullahturhan.model.Role;
import com.abdullahturhan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    List<User> findUserByRole(Role role);
    Optional<User> findUserByEmail(String email);
}
