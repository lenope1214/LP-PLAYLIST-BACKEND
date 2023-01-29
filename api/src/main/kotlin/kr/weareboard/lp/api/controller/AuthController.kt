package kr.weareboard.lp.api.controller

import kr.weareboard.lp.domain.entity.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
) {

//    @GetMapping("/kakao")
//    @ResponseStatus(HttpStatus.OK)
//    fun successKakaoLogin(@RequestParam("token") token: String, response: HttpServletResponse): String {
//        response.addHeader("Authorization", token)
//        response.sendRedirect("http://localhost:5173/oauth2/redirect")
//        return "success kakao login"
//    }

    @PostMapping("/kakao/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logoutKakao(): String {
        return userService.kakaoLogout()
    }
}