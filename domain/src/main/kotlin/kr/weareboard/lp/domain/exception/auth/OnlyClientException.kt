package kr.weareboard.lp.domain.exception.auth

import kr.weareboard.lp.domain.exception.UnauthorizedException

class OnlyClientException: UnauthorizedException("거래처 관리자만 가능합니다.")