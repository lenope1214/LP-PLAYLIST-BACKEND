package kr.weareboard.lp.domain.exception.auth

import kr.weareboard.lp.domain.exception.UnauthorizedException

class OnlyLaundryException: UnauthorizedException("세탁소 관리자만 가능합니다.")