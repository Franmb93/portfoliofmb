package com.franmunozbetanzos.portfolio.dto;

import com.franmunozbetanzos.portfolio.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static com.franmunozbetanzos.portfolio.constant.RoleConstants.ADMIN;
import static com.franmunozbetanzos.portfolio.constant.RoleConstants.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

public class UserInfoDTOTest {

    private static Stream<Arguments> provideUserInfoDTOData() {
        return Stream.of(arguments(2L, "admin", Set.of(ADMIN)), arguments(3L, "user", Set.of(USER)));
    }

    @Test
    public void testUserInfoDTO() {
        Long id = 1L;
        String username = "user";

        Set<Role> roles = mock(Set.class);

        UserInfoDTO userInfoDTO = new UserInfoDTO(id, username, roles);

        assertThat(userInfoDTO.id()).isEqualTo(id);
        assertThat(userInfoDTO.username()).isEqualTo(username);
        assertThat(userInfoDTO.roles()).isEqualTo(roles);
    }

    @ParameterizedTest
    @MethodSource("provideUserInfoDTOData")
    public void testUserInfoDTOParameterized(Long id, String username, Set<Role> roles) {
        UserInfoDTO userInfoDTO = new UserInfoDTO(id, username, roles);

        assertThat(userInfoDTO.id()).isEqualTo(id);
        assertThat(userInfoDTO.username()).isEqualTo(username);
        assertThat(userInfoDTO.roles()).isEqualTo(roles);
    }
}