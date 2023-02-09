package com.cubex.ecommerce.v1.seeders;

import com.cubex.ecommerce.v1.enums.ERole;
import com.cubex.ecommerce.v1.models.Role;
import com.cubex.ecommerce.v1.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findAll().size() == 0){
        roleRepository.save(new Role(ERole.ADMIN,"All endpoints"));
        roleRepository.save(new Role(ERole.NORMAL,"Only what is allowed for him/her"));
        }
    }
}
