package hello.goodnews.controller;

import hello.goodnews.auth.LoginUser;
import hello.goodnews.domain.User;
import hello.goodnews.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    /**
     * 로그인 성공 시 호출되는 엔드포인트
     * @param loginUser 현재 로그인된 사용자
     * @return 프로필 완성 여부에 따른 응답
     */
    @GetMapping("/success")
    public ResponseEntity<?> loginSuccess(@LoginUser User loginUser) {
        log.info("loginuser={}", loginUser.getName());

        if (!userService.isUserProfileComplete(loginUser)) {
            // 프로필 정보가 완성되지 않은 경우
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ProfileStatusResponse(false));
        }

        // 프로필 정보가 완성된 경우
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProfileStatusResponse(true));
    }

    // 응답용 DTO 클래스
    @Data
    @AllArgsConstructor
    static class ProfileStatusResponse {
        private boolean isProfileComplete;
    }
}