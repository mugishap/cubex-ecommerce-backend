package com.cubex.ecommerce.v1.services;

import com.cubex.ecommerce.v1.enums.ERole;
import com.cubex.ecommerce.v1.enums.EUserStatus;
import com.cubex.ecommerce.v1.fileHandling.File;
import com.cubex.ecommerce.v1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;


public interface IUserService {

    public List<User> getAll();

    public User initiateResetPassword(String email);

    public Page<User> getAll(Pageable pageable);

    public User getById(UUID id);

    public User create(User user);

    public User update(UUID id, User user);

    public boolean delete(UUID id);

    public List<User> getAllByRole(ERole role);

    public Page<User> getAllByRole(Pageable pageable, ERole role);

    public List<User> searchUser(String searchKey);

    public Page<User> searchUser(Pageable pageable, String searchKey);

    public User getLoggedInUser();

    public User getByEmail(String email);

    public User changeStatus(UUID id, EUserStatus status);

    public User changeProfileImage(UUID id, File file);

}