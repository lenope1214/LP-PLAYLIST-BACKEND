package kr.co.jsol.domain.exception.entities.user

import kr.co.jsol.domain.exception.BasicException

class UserLaundryInfoNotExist: BasicException(400, "사용자의 세탁소 정보가 지정되어있지 않습니다.")