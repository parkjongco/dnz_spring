package com.kedu.services;

import com.kedu.config.CustomException;
import com.kedu.dao.MembersDAO;
import com.kedu.dto.MembersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MembersService implements UserDetailsService {
    @Autowired
    private MembersDAO membersDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MembersService(PasswordEncoder passwordEncoder, MembersDAO membersDAO) {
        this.passwordEncoder = passwordEncoder;
        this.membersDAO = membersDAO;
    }


    public void registerUser(MembersDTO dto) {
        if (membersDAO.findByEmail(dto.getUserEmail()) != null) {
            throw new CustomException("Email is already taken.");
        }

        if (membersDAO.findByPhoneNumber(dto.getUserPhoneNumber()) != null) {
            throw new CustomException("Phone number is already taken.");
        }

        if (membersDAO.findByUserId(dto.getUserId()) != null) {
            throw new CustomException("User ID is already taken.");
        }

        dto.setUserPw(passwordEncoder.encode(dto.getUserPw()));
        membersDAO.registerUser(dto);
    }


    @Override
    public UserDetails loadUserByUsername(String user_Id) throws UsernameNotFoundException {
        MembersDTO dto = membersDAO.selectById(user_Id);
        if (dto == null) {
            System.out.println("User not found with ID: " + user_Id);
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + dto.getUserId());
        String[] tempRoles = {"ROLE_ADMIN"};
        return new User(dto.getUserId(), dto.getUserPw(), AuthorityUtils.createAuthorityList(tempRoles));
    }


}
