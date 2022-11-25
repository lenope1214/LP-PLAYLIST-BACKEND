package kr.weareboard.lp.domain.exception.auth

import kr.weareboard.lp.domain.exception.UnauthorizedException

class OnlyDepartmentException: UnauthorizedException("부서 관리자만 가능합니다.")