package com.security.springsecurity.mapper;

import com.security.springsecurity.enums.Role;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoleMapper {

    public static Set<Role> getRole(Set<String> roleNames) {
        Set<Role> roleSet = new HashSet<>();

        if(roleNames == null || roleNames.isEmpty()){
            roleSet.add(Role.USER);
        }

        for (String roleName : roleNames) {
            roleSet.add(Role.valueOf(roleName.toUpperCase()));
        }

        return roleSet;
    }


}
