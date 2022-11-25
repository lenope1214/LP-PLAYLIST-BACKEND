package kr.weareboard.lp.domain.exception.jwt

import kr.weareboard.lp.domain.exception.BasicException

class TokenExpiredException : BasicException(401, "로그인 세션이 만료되었습니다.")
