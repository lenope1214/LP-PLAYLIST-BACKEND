package kr.weareboard.lp.domain.entity.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?

//    fun findByUsernameAndDeletedAtIsNull(username: String): User?
}