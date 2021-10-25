package me.danght.workflow.app.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import me.danght.workflow.app.dao.UserInfoRepository;
import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.UserInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author DangHT
 * @date 2021/10/24
 */
@QuarkusTest
public class UserInfoServiceImplTest {

    public static List<UserInfoDO> userInfoDOS;

    @Inject
    UserInfoServiceImpl userInfoService;

    @InjectMock
    UserInfoRepository userInfoRepository;

    @BeforeAll
    public static void initData() {
        userInfoDOS = new ArrayList<>();
        userInfoDOS.add(new UserInfoDO("1", "AAA", "t1", "g1", "u1"));
        userInfoDOS.add(new UserInfoDO("2", "BBB", "t2", "g2", "u2"));
        userInfoDOS.add(new UserInfoDO("3", "CCC", "t3", "g3", "u3"));
    }

    private Optional<UserInfoDO> findById(String id) {
        UserInfoDO result = null;
        for (UserInfoDO userInfoDO : userInfoDOS) {
            if (userInfoDO.getId().equals(id)) {
                result = userInfoDO;
                break;
            }
        }
        return Optional.ofNullable(result);
    }

    private Optional<UserInfoDO> findByUiName(String uiName) {
        UserInfoDO result = null;
        for (UserInfoDO userInfoDO : userInfoDOS) {
            if (userInfoDO.getUiName().equals(uiName)) {
                result = userInfoDO;
                break;
            }
        }
        return Optional.ofNullable(result);
    }

    @BeforeEach
    public void initService() {
        Mockito.when(userInfoRepository.findAll()).thenReturn(userInfoDOS);
        Mockito.when(userInfoRepository.findById(Mockito.anyString())).thenAnswer(new Answer<Optional<UserInfoDO>>() {
            @Override
            public Optional<UserInfoDO> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return findById(invocationOnMock.getArgument(0));
            }
        });
        Mockito.when(userInfoRepository.findByUiName(Mockito.anyString())).thenAnswer(new Answer<Optional<UserInfoDO>>() {
            @Override
            public Optional<UserInfoDO> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return findByUiName(invocationOnMock.getArgument(0));
            }
        });
        userInfoService.setUserInfoRepository(userInfoRepository);
    }

    @Test
    public void testGetAllUsers() {
        Assertions.assertNotNull(userInfoService.getAllUsers());
    }

    @Test
    public void testGetUserById() {
        UserInfoDTO userInfoDTO = userInfoService.getUserById("2");
        Assertions.assertEquals("BBB", userInfoDTO.getUiName());
        Assertions.assertEquals("t2", userInfoDTO.getTenantId());
        Assertions.assertEquals("g2", userInfoDTO.getGiId());
        Assertions.assertEquals("u2", userInfoDTO.getUiNo());
    }

    @Test
    public void testValidateUser() {
        UserInfoDTO aaa = userInfoService.validateUser("AAA");
        UserInfoDTO ddd = userInfoService.validateUser("DDD");
        Assertions.assertNotNull(aaa);
        Assertions.assertNull(ddd);
    }

}
