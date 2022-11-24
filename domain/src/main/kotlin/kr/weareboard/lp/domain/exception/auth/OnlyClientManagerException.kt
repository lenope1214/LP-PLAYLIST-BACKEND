package kr.co.jsol.domain.exception.auth

import kr.co.jsol.domain.exception.UnauthorizedException

class OnlyClientManagerException: UnauthorizedException("거래처, 부서 관리자만 가능합니다.")