package kr.weareboard.lp.domain.entity.user

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.weareboard.lp.domain.entity.user.QUser.user
import org.springframework.stereotype.Component

@Component
// JPAQueryFactory를 사용하려면 QueryDslConfig 파일에 Bean 등록 해줘야함.
class UserQueryRepository (
    private val queryFactory: JPAQueryFactory,
){
    private fun userColumns(): JPAQuery<User> =
        queryFactory.selectFrom(user)
//            .leftJoin(laundry).on(isLaundryEq()).fetchJoin()
//            .leftJoin(client).on(isClientEq()).fetchJoin()
//            .leftJoin(department).on(isDepartmentEq()).fetchJoin()

    fun findByUsername(username: String): User? {
        return userColumns()
            .where(isUsernameEq(username))
            .fetchOne()
    }

    fun findByUserId(userId: Long): User? {
        return userColumns()
            .where(isUserIdEq(userId))
            .fetchOne()
    }


    ////////////// 조건식 함수 모음 /////////////////

    private fun isUserIdEq(userId: Long): BooleanExpression? = user.id.eq(userId)

//    private fun isDepartmentEq(): BooleanExpression? = department.eq(user.department)
//
//    private fun isClientEq(): BooleanExpression? = client.eq(user.client)
//
//    private fun isLaundryEq(): BooleanExpression? = laundry.eq(user.laundry)

    private fun isUsernameEq(username: String): BooleanExpression? =
        user.username.eq(username)

}