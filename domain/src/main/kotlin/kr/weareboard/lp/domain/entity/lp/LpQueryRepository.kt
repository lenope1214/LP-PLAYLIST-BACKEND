package kr.weareboard.lp.domain.entity.lp

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.weareboard.lp.domain.entity.lp.QLp.lp
import kr.weareboard.lp.domain.entity.lp.dto.request.LpSearchCondition
import kr.weareboard.lp.domain.entity.user.QUser.user
import org.springframework.stereotype.Component

@Component
// JPAQueryFactory를 사용하려면 QueryDslConfig 파일에 Bean 등록 해줘야함.
class LpQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {


    fun findByLpPanelId(lpPanelId: Long, lpSearchCondition: LpSearchCondition? = null): Lp? {
        return queryFactory.selectFrom(lp)
            .leftJoin(user).on(lp.writer.eq(user)).fetchJoin()
            .where(
                isLpPanelIdEq(lpPanelId)
            )
            .fetchOne()
    }

    fun findByMyList(receiverId: Long, lpSearchCondition: LpSearchCondition? = null): List<Lp> {
        return queryFactory.selectFrom(lp)
//            .leftJoin(user).on(lp.receiver.eq(user)).fetchJoin()
            .where(
                isReceiverIdEq(receiverId),
                searchCondition(lpSearchCondition)
            )
            .orderBy(lp.id.desc())
            .fetch()
    }

    fun findByMySentList(writer: Long): List<Lp> {
        return queryFactory.selectFrom(lp)
            .leftJoin(user).on(lp.receiver.eq(user)).fetchJoin()
            .where(
                isWriterIdEq(writer)
            )
            .fetch()
    }

    fun findMyNewLpList(userId: Long): List<Lp> {
        return queryFactory.selectFrom(lp)
            .leftJoin(user).on(lp.receiver.eq(user)).fetchJoin()
            .where(
                isReceiverIdEq(userId),
                isReadEq(false)
            )
            .fetch()
    }

    ////////////// 조건식 함수 모음 /////////////////

    private fun searchCondition(condition: LpSearchCondition?): BooleanExpression? {
        var res: BooleanExpression? = null

        if(condition?.title != null){
            res = res?.and(isTitleContains(condition.title)) ?: isTitleContains(condition.title)
        }

        if(condition?.receiverId != null){
            res = res?.and(isReceiverIdEq(condition.receiverId)) ?: isReceiverIdEq(condition.receiverId)
        }

        if(condition?.receiverId != null){
            res = res?.and(isReceiverIdEq(condition.receiverId)) ?: isWriterIdEq(condition.receiverId)
        }
        return res
    }

    private fun isLpPanelIdEq(lpPanelId: Long): BooleanExpression? = lp.id.eq(lpPanelId)

    private fun isReceiverIdEq(receiverId: Long): BooleanExpression? = lp.receiver.id.eq(receiverId)

    private fun isWriterIdEq(writerId: Long): BooleanExpression? = lp.writer.id.eq(writerId)

    private fun isTitleContains(title: String): BooleanExpression? = lp.title.contains(title)

    private fun isReadEq(isRead: Boolean): BooleanExpression? = lp.isRead.eq(isRead)



//    private fun isDepartmentEq(): BooleanExpression? = department.eq(lpPanel.department)
//
//    private fun isClientEq(): BooleanExpression? = client.eq(lpPanel.client)
//
//    private fun isLaundryEq(): BooleanExpression? = laundry.eq(lpPanel.laundry)

//    private fun isLpPanelnameEq(lpPanelname: String): BooleanExpression? =
//        lpPanel.name.eq(lpPanelname)

}