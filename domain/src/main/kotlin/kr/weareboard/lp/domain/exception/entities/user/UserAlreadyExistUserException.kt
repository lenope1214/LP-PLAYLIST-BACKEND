package kr.weareboard.lp.domain.exception.entities.user

import kr.weareboard.lp.domain.exception.BasicException

class UserAlreadyExistUserException : BasicException(409, "이미 회원가입된 아이디입니다.")
