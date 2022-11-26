package kr.weareboard.lp.api.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/auth")
class AuthController {

//    @GetMapping("/kakao")
//    @ResponseStatus(HttpStatus.OK)
//    fun successKakaoLogin(@RequestParam("token") token: String, response: HttpServletResponse): String {
//        response.addHeader("Authorization", token)
//        response.sendRedirect("http://localhost:5173/oauth2/redirect")
//        return "success kakao login"
//    }
}