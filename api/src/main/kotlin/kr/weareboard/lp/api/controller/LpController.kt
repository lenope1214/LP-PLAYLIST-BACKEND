package kr.weareboard.lp.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import kr.weareboard.lp.domain.entity.lp.LpService
import kr.weareboard.lp.domain.entity.lp.dto.request.LpCreateRequest
import kr.weareboard.lp.domain.entity.lp.dto.response.LpReceiverResponse
import kr.weareboard.lp.domain.entity.lp.dto.response.LpResponse
import kr.weareboard.lp.domain.entity.lp.dto.response.LpSenderResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/lp")
class LpController(
    private val lpService: LpService,
){

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "등록 성공"),
        ApiResponse(responseCode = "404", description = "받는 사용자를 찾을 수 없습니다."),
    )
    @Operation(
        summary = "LP 보내기(등록)",
        description = "Bearer 토큰을 보낼 시 보낸 사람 정보도 같이 등록 됨."
    )
    @PostMapping("")
    fun createLp(lpCreateRequest: LpCreateRequest): LpSenderResponse{
        return LpSenderResponse.of(lpService.create(lpCreateRequest))
    }

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공"),
        ApiResponse(responseCode = "404", description = "LP 정보를 찾을 수 없습니다."),
    )
    @Operation(
        summary = "ID로 LP 정보 조회",
    )
    @GetMapping("/{id}")
    fun getLpById(@PathVariable id: Long): LpResponse{
        return LpResponse.of(lpService.findById(id))
    }

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공"),
    )
    @Operation(
        summary = "내가 받은 LP 리스트 조회",
    )
    @GetMapping("/my")
    fun getMyLpList(): List<LpReceiverResponse>{
        return lpService.findByMyList().map { LpReceiverResponse.of(it) }
    }

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공"),
    )
    @Operation(
        summary = "내가 보낸 LP 리스트 조회",
    )
    @GetMapping("/sent")
    fun getMySentLpList(): List<LpSenderResponse>{
        return lpService.findByMySentList().map { LpSenderResponse.of(it) }
    }

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공"),
    )
    @Operation(
        summary = "내가 받은 LP 조회(확인)했음으로 변경",
    )
    @PutMapping("/{id}/read")
    fun readLp(@PathVariable id: Long): LpReceiverResponse{
        return LpReceiverResponse.of(lpService.read(id))
    }

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "삭제 성공"),
    )
    @Operation(
        summary = "내가 받은 LP 정보 삭제",
    )
    @DeleteMapping("/{id}")
    fun deleteLp(@PathVariable id: Long): Unit{
        lpService.deleteLp(id)
    }
}