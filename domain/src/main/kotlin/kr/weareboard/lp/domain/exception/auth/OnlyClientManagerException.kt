package kr.weareboard.lp.domain.exception.auth

import kr.weareboard.lp.domain.exception.UnauthorizedException

class OnlyClientManagerException: UnauthorizedException("거래처, 부서 관리자만 가능합니다.")