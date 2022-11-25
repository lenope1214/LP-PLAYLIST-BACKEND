package kr.weareboard.lp.domain.exception.entities.user

import kr.weareboard.lp.domain.exception.BasicException

class UserDisableException : BasicException(400, "사용 불가능한 계정 정보입니다.")
