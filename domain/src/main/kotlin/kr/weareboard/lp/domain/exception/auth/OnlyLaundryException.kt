package kr.co.jsol.domain.exception.auth

import kr.co.jsol.domain.exception.UnauthorizedException

class OnlyLaundryException: UnauthorizedException("세탁소 관리자만 가능합니다.")