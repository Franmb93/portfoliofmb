package com.franmunozbetanzos.portfolio.service;

import com.franmunozbetanzos.portfolio.dto.UserInfoDTO;
import com.franmunozbetanzos.portfolio.mapper.UserToUserInfoDTOMapper;
import com.franmunozbetanzos.portfolio.repository.UserRepository;
import com.franmunozbetanzos.portfolio.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserToUserInfoDTOMapper userMapper;
    private final UserRepository userRepository;


    @Override
    public List<UserInfoDTO> getAllUsersInfo() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserInfoDTO getUserInfoById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserInfoDTO)
                .orElse(null);
    }
}
