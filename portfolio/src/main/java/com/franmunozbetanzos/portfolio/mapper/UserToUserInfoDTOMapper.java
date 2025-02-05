package com.franmunozbetanzos.portfolio.mapper;

import com.franmunozbetanzos.portfolio.dto.UserInfoDTO;
import com.franmunozbetanzos.portfolio.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToUserInfoDTOMapper {

    UserInfoDTO toUserInfoDTO(User user);
}
