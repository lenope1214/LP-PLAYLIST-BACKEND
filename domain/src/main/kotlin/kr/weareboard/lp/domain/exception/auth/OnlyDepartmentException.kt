package kr.co.jsol.domain.exception.auth

import kr.co.jsol.domain.exception.UnauthorizedException

class OnlyDepartmentException: UnauthorizedException("부서 관리자만 가능합니다.")