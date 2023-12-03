package com.abdullahturhan.config;

import com.abdullahturhan.dto.CreateUserResponse;
import com.abdullahturhan.dto.SearchUserResponse;
import com.abdullahturhan.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(getUserEmailFromUserService(email));
        return user.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private User getUserEmailFromUserService(String email){
        String userServiceUrl = "http://localhost:8081";
        String endpointUrl = userServiceUrl + "/api/users/via";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endpointUrl).queryParam("email",email);
        try {
            ResponseEntity<User> responseEntity = restTemplate.getForEntity(builder.toUriString(), User.class);
            return responseEntity.getBody();
        }catch (Exception e){
            throw new RuntimeException("Exception = "+ e.getMessage());
        }
    }
}
