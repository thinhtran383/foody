package com.example.foodordering.services;

import com.example.foodordering.dtos.UpdateUserDTO;
import com.example.foodordering.dtos.UserDTO;
import com.example.foodordering.entities.*;
import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.repositories.RoleRepository;
import com.example.foodordering.repositories.UserInfoRepository;
import com.example.foodordering.repositories.UserRepository;
import com.example.foodordering.repositories.UserRoleRepository;
import com.example.foodordering.response.user.UserResponse;
import com.example.foodordering.utils.JwtGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleIdRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);

        return users;
    }

    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        String username = userDTO.getUsername();

        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        // check info user is already exist
        if (userInfoRepository.existsByPhone(userDTO.getPhoneNumber()) || userInfoRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Phone number or Email already exists");
        }

        // Get role by ID
        Role role = roleRepository.findRoleById(userDTO.getRole())
                .orElseThrow(() -> new DataIntegrityViolationException("Role does not exist"));


        // Create User entity
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(new LinkedHashSet<>())  // Initialize with mutable collection
                .build();

        // Save User entity to generate ID
        newUser = userRepository.save(newUser);

        // Create UserInfo entity
        UserInfo userInfo = UserInfo.builder()
                .users(newUser)
                .address(userDTO.getAddress())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhoneNumber())
                .build();

        // Set userInfo to user
        newUser.setUserInfo(userInfo);

        // Save UserInfo entity
        userInfoRepository.save(userInfo);

        // Set role for user
        newUser.getRoles().add(role);

        // Create UserRole entity
        UserRole userRole = UserRole.builder()
                .id(new UserRoleId(newUser.getId(), role.getId()))
                .user(newUser)
                .role(role)
                .build();

        // Save UserRole entity
        userRoleRepository.save(userRole);

        // Save User entity again with updated relationships
        return userRepository.save(newUser);
    }


    @Transactional
    public String login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);


        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("User not exist");
        }

        User userExist = optionalUser.get();

        if (!passwordEncoder.matches(password, userExist.getPassword())) {
            throw new BadCredentialsException("Password not match");
        }


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password, userExist.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);

        String token = jwtGenerator.generateToken(userExist);

        return token;
    }

    @Transactional
    public User getUserDetailFromToken(String token) throws Exception {
        String username = jwtGenerator.extractUsername(token);


        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("User not found");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateInfo(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        Optional<User> existingUser = userRepository.findById(userId);

        // check user is existed
        if (existingUser.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }

        UserInfo userInfoExisting = existingUser.get().getUserInfo();
        // update user
        if (updatedUserDTO.getFullname() != null) {
            userInfoExisting.setName(updatedUserDTO.getFullname());
        }

        if (updatedUserDTO.getPhoneNumber() != null) {
            userInfoExisting.setPhone(updatedUserDTO.getPhoneNumber());
        }

        if (updatedUserDTO.getAddress() != null) {
            userInfoExisting.setAddress(updatedUserDTO.getAddress());
        }

        if(updatedUserDTO.getEmail() != null){
            userInfoExisting.setEmail(updatedUserDTO.getEmail());
        }

        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if (!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())){ // check retype password
                throw new DataNotFoundException("Password not match");
            }

            String newPassword = updatedUserDTO.getPassword();
            String encodePassword = passwordEncoder.encode(newPassword);

            existingUser.get().setPassword(encodePassword);

        }

        existingUser.get().setUserInfo(userInfoExisting);
        return userRepository.save(existingUser.get());

    }



}
