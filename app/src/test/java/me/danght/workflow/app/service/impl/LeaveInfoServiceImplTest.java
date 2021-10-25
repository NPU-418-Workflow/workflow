package me.danght.workflow.app.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import me.danght.workflow.app.convert.LeaveInfoConvert;
import me.danght.workflow.app.dao.LeaveInfoRepository;
import me.danght.workflow.app.dao.UserInfoRepository;
import me.danght.workflow.app.dataobject.LeaveInfoDO;
import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.LeaveInfoDTO;
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
public class LeaveInfoServiceImplTest {

    public static List<LeaveInfoDO> leaveInfoDOS;

    @Inject
    LeaveInfoServiceImpl leaveInfoService;

    @InjectMock
    LeaveInfoRepository leaveInfoRepository;

    @InjectMock
    UserInfoRepository userInfoRepository;

    @BeforeAll
    public static void initData() {
        leaveInfoDOS = new ArrayList<>();
        leaveInfoDOS.add(new LeaveInfoDO("1", "u1", 3));
    }

    private Optional<LeaveInfoDO> findById(String id) {
        LeaveInfoDO result = null;
        for (LeaveInfoDO leaveInfoDO : leaveInfoDOS) {
            if (leaveInfoDO.getId().equals(id)) {
                result = leaveInfoDO;
                break;
            }
        }
        return Optional.ofNullable(result);
    }

    @BeforeEach
    public void initService() {
        leaveInfoService.setLeaveInfoRepository(leaveInfoRepository);
        leaveInfoService.setUserInfoRepository(userInfoRepository);
        Mockito.when(leaveInfoRepository.save(Mockito.any())).thenAnswer(new Answer<LeaveInfoDTO>() {
            @Override
            public LeaveInfoDTO answer(InvocationOnMock invocationOnMock) throws Throwable {
                LeaveInfoDO leaveInfoDO = invocationOnMock.getArgument(0);
                leaveInfoDOS.add(leaveInfoDO);
                return LeaveInfoConvert.INSTANCE.convertDOToDTO(leaveInfoDO);
            }
        });
        Mockito.when(leaveInfoRepository.findById(Mockito.anyString())).thenAnswer(new Answer<Optional<LeaveInfoDO>>() {
            @Override
            public Optional<LeaveInfoDO> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return findById(invocationOnMock.getArgument(0));
            }
        });
        Mockito.when(userInfoRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new UserInfoDO()));
    }

    @Test
    public void testAddLeave() {
        Assertions.assertNotNull(leaveInfoDOS);
        Assertions.assertEquals(1, leaveInfoDOS.size());

        LeaveInfoDTO leaveInfoDTO = new LeaveInfoDTO();
        leaveInfoDTO.setId("2");
        leaveInfoDTO.setUiId("u2");
        leaveInfoDTO.setUiName("AAA");
        leaveInfoDTO.setDurations(5);
        leaveInfoService.addLeave(leaveInfoDTO);
        Assertions.assertEquals(2, leaveInfoDOS.size());

        LeaveInfoDO leaveInfoDO = leaveInfoDOS.get(1);
        Assertions.assertEquals("2", leaveInfoDO.getId());
        Assertions.assertEquals(5, leaveInfoDO.getDurations());
    }

    @Test
    public void testGetLeaveInfoById() {
        LeaveInfoDTO result = leaveInfoService.getLeaveInfoById("1");
        Assertions.assertEquals("1", result.getId());
        Assertions.assertEquals(3, result.getDurations());
    }

}
