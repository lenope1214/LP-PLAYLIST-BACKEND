package kr.weareboard.lp.domain.entity.lp

import org.springframework.data.jpa.repository.JpaRepository

interface LpRepository : JpaRepository<Lp, Long> {
}