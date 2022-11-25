package kr.weareboard.lp.domain.exception.jwt

import kr.weareboard.lp.domain.exception.BasicException

class TokenIsNotValidException : BasicException(400, "토큰정보가 비정상입니다.")
