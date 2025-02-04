package com.franmunozbetanzos.portfolio.service.api;

import com.franmunozbetanzos.portfolio.dto.UserInfoDTO;

import java.util.List;

public interface UserService {
    /**
     * Retrieves a list of UserInfoDTO objects for all users.
     *
     * @return a list of UserInfoDTO objects representing all users.
     */
    List<UserInfoDTO> getAllUsersInfo();

    /**
     * Retrieves the UserInfoDTO for a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the UserInfoDTO of the user, or null if the user is not found
     */
    UserInfoDTO getUserInfoById(Long id);
}
