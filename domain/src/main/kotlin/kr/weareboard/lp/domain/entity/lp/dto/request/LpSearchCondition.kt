package kr.weareboard.lp.domain.entity.lp.dto.request

import java.time.LocalDate

data class LpSearchCondition(
    val title: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val receiverId: Long? = null,
    val writerId: Long? = null,
)