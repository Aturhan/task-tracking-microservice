package com.abdullahturhan.service;

import com.abdullahturhan.dto.CreateUserRequest;
import com.abdullahturhan.dto.CreateUserResponse;
import com.abdullahturhan.dto.SearchUserResponse;
import com.abdullahturhan.exception.UserNotFoundException;
import com.abdullahturhan.model.Role;
import com.abdullahturhan.model.User;
import com.abdullahturhan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SearchUserResponse findUserById(String id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        return SearchUserResponse.converter(user);
    }

    public SearchUserResponse findUserByEmail(String email) throws UserNotFoundException{
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: "+ email));
        if (user.getRole().getValue().equals(Role.USER.getValue())){
            log.info("User fetching by email=  " + email);
            return SearchUserResponse.converter(user);
        }
        return null;
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: "+ email));
    }

    public List<SearchUserResponse> findUserByRole(Role role) throws UserNotFoundException {

        List<User> userList = userRepository.findUserByRole(role);
        if (!userList.isEmpty()){
           return userList.stream().map(SearchUserResponse::converter)
                   .collect(Collectors.toList());
        }
        throw new UserNotFoundException("User not found with role " + role);
    }
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request){
      final User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .role(request.role())
                .build();
      final User savedUser = userRepository.save(user);
      return CreateUserResponse.converter(savedUser);
    }

    @Transactional
    public void deleteUserById(String id) throws UserNotFoundException{
        User user = userRepository.findById(id)
                .orElseThrow(() ->  new UserNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }
}
