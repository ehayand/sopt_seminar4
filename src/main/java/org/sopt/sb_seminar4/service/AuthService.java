package org.sopt.sb_seminar4.service;

import org.sopt.sb_seminar4.dto.User;
import org.sopt.sb_seminar4.mapper.UserMapper;
import org.sopt.sb_seminar4.model.DefaultRes;
import org.sopt.sb_seminar4.model.LoginReq;
import org.sopt.sb_seminar4.utils.ResponseMessage;
import org.sopt.sb_seminar4.utils.StatusCode;
import org.springframework.stereotype.Service;

/**
 * Created by ehay@naver.com on 2018-11-17
 * Blog : http://ehay.tistory.com
 * Github : http://github.com/ehayand
 */

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthService(UserMapper userMapper, JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public DefaultRes<JwtService.TokenRes> login(final LoginReq loginReq) {
        final User user = userMapper.findByNameAndPassword(loginReq.getName(), loginReq.getPassword());
        if (user != null) {
            //토큰 생성
            final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.create(user.getUserIdx()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }

        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}
