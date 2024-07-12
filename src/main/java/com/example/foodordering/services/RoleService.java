package com.example.foodordering.services;

import com.example.foodordering.entities.Role;
import com.example.foodordering.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public List<String> getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
    }
}
