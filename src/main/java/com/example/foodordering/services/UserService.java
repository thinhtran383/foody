package com.example.foodordering.services;

import com.example.foodordering.dtos.UpdateUserDTO;
import com.example.foodordering.dtos.UserDTO;
import com.example.foodordering.entities.*;
import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.repositories.*;
import com.example.foodordering.response.user.UserResponse;
import com.example.foodordering.utils.JwtGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        Page<UserResponse> result = users.map(user -> modelMapper.map(user, UserResponse.class));
        return result;
    }


    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        String username = userDTO.getUsername();

        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        // check info user is already exist
        if (userRepository.existsByEmailOrPhoneNumber(userDTO.getEmail(), userDTO.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number or Email already exists");
        }

        // Get role by ID
        Role role = roleRepository.findRoleById(userDTO.getRole())
                .orElseThrow(() -> new DataIntegrityViolationException("Role does not exist"));


        // Create User entity
        User newUser = User.builder()
                .fullname(userDTO.getFullname())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .username(username)
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(role)
                .build();

        // Save User entity to generate ID
        newUser = userRepository.save(newUser);

        return userRepository.save(newUser);
    }


    @Transactional
    public String login(String username, String password) throws Exception {
        User userExist = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not exist")); // 1


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

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new DataNotFoundException("User not found"));

        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateInfo(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        // update user
        if (updatedUserDTO.getFullname() != null) {
            existingUser.setFullname(updatedUserDTO.getFullname());
        }

        if (updatedUserDTO.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUserDTO.getPhoneNumber());
        }

        if (updatedUserDTO.getAddress() != null) {
            existingUser.setAddress(updatedUserDTO.getAddress());
        }

        if (updatedUserDTO.getEmail() != null) {
            existingUser.setEmail(updatedUserDTO.getEmail());
        }

        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if (!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) { // check retype password
                throw new DataNotFoundException("Password not match");
            }

            String newPassword = updatedUserDTO.getPassword();
            String encodePassword = passwordEncoder.encode(newPassword);

            existingUser.setPassword(encodePassword);

        }

        return userRepository.save(existingUser);

    }


    @Transactional
    public User deleteUserByUserId(long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        boolean isAdmin = user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.toString().equals("ROLE_ADMIN"));
        if (isAdmin) {
            throw new IllegalStateException("Cannot delete admin account");
        }


        userRepository.delete(user);
        return user;


//        tokenRepository.deleteByUser(user);
//        userInfoRepository.delete(user.getUserInfo());
//
//
//        userRoleIdRepository.deleteByUser(user);
//        userRepository.deleteById(userId);


    }
}
