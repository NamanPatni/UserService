package org.example.service;

import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.example.dto.LoginRequestDto;
import org.example.dto.SignUpRequestDto;
import org.example.dto.UserResponseDto;
import org.example.exceptions.InvalidEmailException;
import org.example.exceptions.InvalidPasswordException;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.models.Role;
import org.example.models.Status;
import org.example.models.User;
import org.example.repository.IRoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.random.RandomGenerator;


@Service
public class UserAuthService implements IUserAuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SecretKey secretKey;

    @Override
    public User createUser(String email, String password, String userName, String phoneNumber) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists with the given email");
        }

        User user = new User();
        user.setName(userName);
        user.setEmail(email);

        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);

        //Setting the role for a user

        // step 1 : Will look into the database weather the role is already exist in db
        List<Role> roleList = new ArrayList<>();
            Optional<Role> optionalRole = roleRepository.getRoleByType("non_admin");
            if (optionalRole.isPresent()){
                roleList.add(optionalRole.get());
                user.setRoleList(roleList);
            }else {
                Role role = new Role();
                List<User> userList = new ArrayList<>();
                roleList.add(role);
                role.setId(RandomGenerator.getDefault().nextLong());
                role.setType("non_admin");
                role.setStatus(Status.ACTIVE);
                user.setRoleList(roleList);
                user.setCreatedAt(new Date());
                role.setUserList(userList);
                roleRepository.save(role);
            }
            userRepository.save(user);

        return user;
    }

    @Override
    public Pair<User, String> login(String email, String password) {

        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()){
            throw new InvalidEmailException("User does not exist please do sign up");
        }

        if(bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())){

            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(optionalUser.get().getUserId()));
            map.put("createdBy", optionalUser.get().getName());
            map.put("lastUpdatedAt", String.valueOf(new Date().getTime()));
            map.put("expiryAt", String.valueOf(new Date().getTime()));

            //Map
            String token = Jwts.builder().claims(map).signWith(secretKey).compact();

            //jwtBuilder.header().equals()
            // Steps for generating the token is
            // 1. Header algo for encrypt
            // 2. Payload for passing the value for subsequent calls
            // 3. Signature to generate

            //Adding dependencies used for jwt

            return new Pair<>(optionalUser.get(), token);
        } else {
            throw new InvalidPasswordException("Password is incorrect please retry");
        }
    }
}