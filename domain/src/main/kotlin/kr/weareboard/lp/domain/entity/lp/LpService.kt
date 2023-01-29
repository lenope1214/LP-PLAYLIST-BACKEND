package kr.weareboard.lp.domain.entity.lp

import kr.weareboard.lp.domain.entity.lp.dto.request.LpCreateRequest
import kr.weareboard.lp.core.storage.StorageService
import kr.weareboard.lp.domain.entity.user.User
import kr.weareboard.lp.domain.entity.user.UserRepository
import kr.weareboard.lp.domain.entity.user.UserService
import kr.weareboard.lp.domain.exception.MyEntityNotFoundException
import kr.weareboard.lp.domain.exception.UnauthorizedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class LpService(
    private val userRepository: UserRepository,
    private val lpRepository: LpRepository,
    private val lpQueryRepository: LpQueryRepository,

    private val storageService: StorageService,
) {

    fun create(lpCreateRequest: LpCreateRequest): Lp{
        var loginUser: User? = null
        try{
            loginUser = UserService.getAccountFromSecurityContext()
        }catch(ignore: UnauthorizedException){}
        val receiver = userRepository.findById(lpCreateRequest.receiverId ?: 0).orElseThrow{throw MyEntityNotFoundException("받는 유저가 존재하지 않습니다.")}
        val lp = lpCreateRequest.toEntity().let {
            it.writer = loginUser
            it.receiver = receiver
            lpRepository.save(it)
        }

        lpCreateRequest.coverImgFile?.let {
            saveImgFile(lp, it)
        }

        return lp
    }

    fun saveImgFile(lp: Lp, file: MultipartFile): Unit{
        try {
            lp.updateImgPath(storageService.store(file, lp.id.toString()), null)
            lpRepository.save(lp)
        }catch(ignore: Exception){}
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Lp {
        return lpRepository.findById(id).orElseThrow{
            throw MyEntityNotFoundException("해당 LP가 존재하지 않습니다.")
        }
    }

    @Transactional(readOnly = true)
    fun findByMyList(): List<Lp>{
        val loginUser: User = UserService.getAccountFromSecurityContext()
        return lpQueryRepository.findByMyList(loginUser.id!!)
    }

    @Transactional(readOnly = true)
    fun findByMySentList(): List<Lp>{
        val loginUser: User = UserService.getAccountFromSecurityContext()
        return lpQueryRepository.findByMySentList(loginUser.id!!)
//            .map {
//                it.writer = null
//                it
//            }
    }

    fun deleteLp(lpId: Long): Unit{
        val loginUser: User = UserService.getAccountFromSecurityContext()

        findById(lpId).let {
            if(it.writer?.id == loginUser.id
                || it.receiver?.id == loginUser.id){
                it.softDelete(loginUser.email)
                lpRepository.save(it)
            }
        }
    }

    fun read(id: Long): Lp {
        val lp = findById(id)
        lp.updateRead(true)
        return lpRepository.save(lp)
    }
}