package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.LoginUserRequestDto;
import com.springboot.product_shop.dtos.UserSimplifiedRequestDto;
import com.springboot.product_shop.dtos.UserRequestDto;
import com.springboot.product_shop.dtos.UserResponseDto;
import com.springboot.product_shop.enums.RoleEnum;
import com.springboot.product_shop.exceptions.*;
import com.springboot.product_shop.mappers.UserMapper;
import com.springboot.product_shop.models.Role;
import com.springboot.product_shop.models.UserEntity;
import com.springboot.product_shop.repositories.UserRepository;
import com.springboot.product_shop.security.CustomUserDetails;
import com.springboot.product_shop.security.util.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Autowired
    public UserServiceImp(UserRepository userRepository, UserMapper userMapper, RoleService roleService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto getUserByIdForSelf() {
        UserEntity currentLoggedUser= getCurrentLoggedUser();
        System.out.println("Id: "+currentLoggedUser.getId());
        return getUserById(currentLoggedUser.getId());
    }

    private UserEntity getCurrentLoggedUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            System.out.println("Unauthorized access!");
            throw new AccessDeniedException("Unauthorized access!");
        }
        if(!(userDetails instanceof CustomUserDetails customUserDetails)){
            System.out.println("UserDetails is not an instance of CustomUserDetails!");
            throw new ClassCastException("UserDetails is not an instance of CustomUserDetails!");
        }
        return customUserDetails.getUser();
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        UserEntity user= findUserById(id);
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByDocument(String document) {
        UserEntity user= userRepository.findByDocument(document).orElseThrow(()-> new UserDocumentNotFoundException("User with document: "+document+" not found!"));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        UserEntity user= userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username: "+username+" not found!"));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public Page<UserResponseDto> getUsersByUsernameContaining(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCase(username,pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public Page<UserResponseDto> getUsersByFullNameContaining(String fullName, Pageable pageable) {
        return userRepository.findByFullNameContainingIgnoreCase(fullName,pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public String loginUser(LoginUserRequestDto loginUserRequestDto) {
        String username= loginUserRequestDto.getUsername();
        String password= loginUserRequestDto.getPassword();
        var passwordAuthenticationToken= new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication= authenticationManager.authenticate(passwordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.createToken(authentication);
    }

    public UserEntity findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" + id + " not found!"));
    }

    @Override
    @Transactional
    public UserResponseDto registerUserCustomerForSelf(UserSimplifiedRequestDto userSimplifiedRequestDto) {
        UserEntity user= userMapper.toUser(userSimplifiedRequestDto);

        validateUniqueFields(user);

        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role= roleService.findRoleByRoleEnum(RoleEnum.CUSTOMER);
        user.getRoles().add(role);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        UserEntity user= userMapper.toUser(userRequestDto);
        List<UUID> roleIds= userRequestDto.getRoleIds();

        validateUniqueFields(user);

        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        List<Role> roles= getRoles(roleIds);
        user.setRoles(roles);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    private List<Role> getRoles(List<UUID> roleIds) {
        List<Role> roles= roleService.findRoleByIds(roleIds);
        if(roles.isEmpty()){
            throw new InvalidRoleException("Roles are not valid!");
        }
        return roles;
    }

    private void validateUniqueFields(UserEntity user) {
        if(userRepository.existsByDocument(user.getDocument())){
            throw new UserDocumentExistsException("User with Document: "+ user.getDocument()+" already exists!");
        }
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UsernameExistsException("User with Username: "+ user.getUsername()+" already exists!");
        }
        if(userRepository.existsByEmailIgnoreCase(user.getEmail())){
            throw new UserEmailExistsException("User with Email: "+ user.getEmail()+" already exists!");
        }
    }

    @Override
    @Transactional
    public UserResponseDto updateUserForSelf(UserSimplifiedRequestDto userSimplifiedRequestDto) {
        UserEntity user= userMapper.toUser(userSimplifiedRequestDto);

        UserEntity recoveredUser= findUserById(getCurrentLoggedUser().getId());
        validateFieldsUpdateConflict(user, recoveredUser);

        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        BeanUtils.copyProperties(user,recoveredUser,"id","roles");//Note: Ignore orders field if you use that in UserEntity
        return userMapper.toUserResponseDto(recoveredUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto) {
        UserEntity user= userMapper.toUser(userRequestDto);
        List<UUID> roleIds= userRequestDto.getRoleIds();

        UserEntity recoveredUser= findUserById(id);
        validateFieldsUpdateConflict(user, recoveredUser);

        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        List<Role> roles= getRoles(roleIds);
        user.setRoles(roles);

        BeanUtils.copyProperties(user,recoveredUser,"id");//Note: Ignore orders field if you use that in UserEntity
        return userMapper.toUserResponseDto(recoveredUser);
    }

    private void validateFieldsUpdateConflict(UserEntity user, UserEntity recoveredUser) {
        if(userDocumentExistsForAnotherUser(user.getDocument(), recoveredUser)){
            throw new UserDocumentExistsException("User with Document: "+ user.getDocument()+" already exists!");
        }
        if(usernameExistsForAnotherUser(user.getUsername(), recoveredUser)){
            throw new UsernameExistsException("User with Username: "+ user.getUsername()+" already exists!");
        }
        if(userEmailExistsForAnotherUser(user.getEmail(), recoveredUser)){
            throw new UserEmailExistsException("User with Email: "+ user.getEmail()+" already exists!");
        }
    }

    private boolean userDocumentExistsForAnotherUser(String document, UserEntity recoveredUser) {
        return userRepository.existsByDocumentAndIdNot(document,recoveredUser.getId());
    }

    private boolean usernameExistsForAnotherUser(String username, UserEntity recoveredUser) {
        return userRepository.existsByUsernameAndIdNot(username,recoveredUser.getId());
    }

    private boolean userEmailExistsForAnotherUser(String email, UserEntity recoveredUser) {
        return userRepository.existsByEmailIgnoreCaseAndIdNot(email,recoveredUser.getId());
    }

    /*@Override
    @Transactional
    public void deleteUser(UUID id) {
        UserEntity user= findUserById(id);
        userRepository.delete(user);
    }*/

}
