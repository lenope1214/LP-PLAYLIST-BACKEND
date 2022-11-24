package kr.co.jsol.domain.exception.jwt

import kr.co.jsol.domain.exception.BasicException

class TokenIsNotValidException : BasicException(400, "토큰정보가 비정상입니다.")
