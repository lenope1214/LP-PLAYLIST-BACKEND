package kr.weareboard.lp.domain.exception.jwt

import kr.weareboard.lp.domain.exception.BasicException

class TokenIsNotGiven(name: String) : BasicException(400, name + "토큰이 넘어오지 않았습니다.")
