package kr.weareboard.lp.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import kr.weareboard.lp.domain.entity.user.UserService
import kr.weareboard.lp.domain.entity.user.dto.response.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
) {

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
        ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다."),
    )
    @Operation(
        summary = "사용자 자신 정보 조회",
        description = "Bearer 토큰을 같이 보내야 함"
    )
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getUserInfo(): UserResponse {
        return userService.getMyInfo()
    }
}