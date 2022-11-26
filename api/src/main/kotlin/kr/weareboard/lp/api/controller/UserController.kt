package kr.weareboard.lp.api.controller

import kr.weareboard.lp.domain.entity.user.UserService
import kr.weareboard.lp.domain.entity.user.dto.response.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
) {


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getUserInfo(): UserResponse {
        return userService.getMyInfo()
    }
}