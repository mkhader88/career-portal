package com.edu.miu.Repo;


import com.edu.miu.model.User;
import com.edu.miu.payload.Requests.LoginRequest;
import com.edu.miu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    void isPersonExitsByEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("abdeenov");
        loginRequest.setPassword("123");
        User actualResult = userRepo.findByEmail(loginRequest.getUsernameOrEmail()).get();
        assert(actualResult.getEmail()).equals(loginRequest.getUsernameOrEmail());
    }
}
