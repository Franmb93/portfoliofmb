package com.franmunozbetanzos.portfolio.dto;

import com.franmunozbetanzos.portfolio.model.Role;

import java.util.Set;

/**
 * Data transfer object for user information.
 */
public record UserInfoDTO(Long id, String username, Set<Role> roles) {

}
