package shop.mtcoding.thereblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.thereblog.core.auth.MyUserDetails;
import shop.mtcoding.thereblog.core.exception.ssr.Exception400;
import shop.mtcoding.thereblog.core.exception.ssr.Exception403;
import shop.mtcoding.thereblog.dto.user.UserRequest;
import shop.mtcoding.thereblog.model.user.User;
import shop.mtcoding.thereblog.service.UserService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    // 인증이 되지 않은 상태에서 인증과 관련된 주소는 앞에 엔티티 적지 마세요.
    // write (post) : /리소스/식별자(pk, uk)/save or delete or update
    // read (get) : /리소스/식별자(pk, uk)
    @PostMapping("/join")
    public String join(UserRequest.JoinInDTO joinInDTO){ // x-www-form-urlencoded
        userService.회원가입(joinInDTO);
        return "redirect:/loginForm"; // 302
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/s/user/{id}/updateProfileForm")
    public String profileUpdateForm(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){
        // 1. 권한 체크
        if(id != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }
        User userPS = userService.회원프로필보기(id);
        model.addAttribute("user", userPS);
        return "user/profileUpdateForm"; // 뷰리졸버
    }

    @PostMapping("/s/user/{id}/updateProfile")
    public String profileUpdate(
            @PathVariable Long id,
            MultipartFile profile,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ){
        // 1. 권한 체크
        if(id != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }

        // 2. 사진 파일 유효성 검사
        if(profile.isEmpty()){
            throw new Exception400("profile", "사진이 전송되지 않았습니다");
        }

        // 3. 사진을 파일에 저장하고 그 경로를 DB에 저장
        User userPS = userService.프로필사진수정(profile, id);

        // 4. 세션 동기화
        myUserDetails.setUser(userPS);
        session.setAttribute("sessionUser", userPS);

        return "redirect:/";
    }
}
