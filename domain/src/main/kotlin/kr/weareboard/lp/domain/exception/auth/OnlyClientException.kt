package kr.co.jsol.domain.exception.auth

import kr.co.jsol.domain.exception.UnauthorizedException

class OnlyClientException: UnauthorizedException("거래처 관리자만 가능합니다.")