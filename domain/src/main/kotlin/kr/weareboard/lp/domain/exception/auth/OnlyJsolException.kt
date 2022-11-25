package kr.weareboard.lp.domain.exception.auth

import kr.weareboard.lp.domain.exception.UnauthorizedException

class OnlyJsolException(msg: String? = null): UnauthorizedException(msg ?: "제이솔루션 관리자만 가능합니다.")