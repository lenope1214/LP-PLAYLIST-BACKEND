package kr.weareboard.lp.domain.entity.user

import kr.weareboard.lp.domain.entity.user.dto.request.*
import kr.weareboard.lp.domain.entity.user.dto.response.LoginResponse
import kr.weareboard.lp.domain.entity.user.dto.response.UserResponse
import kr.weareboard.lp.domain.entity.user.enum.OAuth2Provider
import kr.weareboard.lp.domain.entity.user.enum.UserRoleType
import kr.weareboard.lp.domain.entity.util.findByIdOrThrow
import kr.weareboard.lp.domain.exception.BasicException
import kr.weareboard.lp.domain.exception.UnauthorizedException
import kr.weareboard.lp.domain.exception.entities.user.UserAlreadyExistUserException
import kr.weareboard.lp.domain.jwt.JwtTokenProvider
import kr.weareboard.lp.domain.jwt.dto.JwtToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.Lock
import org.springframework.security.authentication.*
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.LockModeType

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val userQueryRepository: UserQueryRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): JwtToken {
        val userPk = jwtTokenProvider.getUserPk(refreshTokenRequest.refreshToken)
        return JwtToken(jwtTokenProvider.createAccessToken(userPk), jwtTokenProvider.createRefreshToken(userPk))
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        val user: User = userQueryRepository.findByUsername(loginRequest.username)
            ?: throw BasicException(403, "로그인 실패하셨습니다.")

        try {
//            log.info("username : ${user.username}\npassword : ${loginRequest.password}")
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    user.username,
                    loginRequest.password
                )
            )

            user.login()
//            log.info("user : ${user.username} is logged in")
            userRepository.save(user)
        } catch (e: AuthenticationException) {
            e.printStackTrace()
            throw BasicException(403, "로그인 실패하셨습니다.")
        } catch (e: LockedException) {
            throw BasicException(403, "계정이 잠겨 있습니다.")
        } catch (e: DisabledException) {
            throw BasicException(403, "계정이 비활성화 상태입니다.")
        } catch (e: CredentialsExpiredException) {
            throw BasicException(403, "비밀번호가 만료 되었습니다.")
        } catch (e: AccountExpiredException) {
            throw BasicException(403, "계정이 만료되었습니다.")
        }


        return LoginResponse(
            username = user.username,
//            laundryId = user.getLaundry()?.id,
//            laundryName = user.getLaundry()?.name,
//            clientId = user.getClient()?.id,
//            clientName = user.getClient()?.name,
//            departmentId = user.getDepartment()?.id,
//            departmentName = user.getDepartment()?.name,
            userId = user.id!!,
            name = user.name,
            role = user.role,
            accessToken = jwtTokenProvider.createAccessToken(user.username),
            refreshToken = jwtTokenProvider.createRefreshToken(user.username),
        )
    }

    fun isExistUser(username: String): Unit {
        try {
            getUserByUsername(username)
            // exception이 throw 되지 않았다면 존재하므로 에러 throw
            throw UserAlreadyExistUserException()
        } catch (ignore: IllegalArgumentException) {
            // throw 되면 존재 하지 않으므로 정상 처리.
        }
    }



//    fun createUser(userCreateRequest: UserCreateRequest): UserResponse {
//
//        val encryptPassword = passwordEncoder.encode(userCreateRequest.password)
//        userCreateRequest.encryptedPassword = encryptPassword
//
//        isExistUser(userCreateRequest.username)
//
//        val user = User(
//            username = userCreateRequest.username,
//            password = encryptPassword,
//            name = userCreateRequest.name,
//            role = userCreateRequest.role,
//            phone = userCreateRequest.phone,
//            email = userCreateRequest.email,
//        )
//
//        try {
//            val loginUser = getAccountFromSecurityContext()
//            user.createdBy(loginUser.username)
////            if (loginUser.getLaundry() != null && loginUser.getLaundry()?.id != null) user.updateLaundry(loginUser.getLaundry()!!)
//        } catch (ignore: Exception) {
//            log.error("사용자 등록 - 세탁소 정보 지정 중 에러발생!")
//            ignore.printStackTrace()
//        }
//
//        try {
//            val loginUser = getAccountFromSecurityContext()
////            if (loginUser.getClient() != null && loginUser.getClient()?.id != null) user.updateClient(loginUser.getClient()!!)
//        } catch (ignore: Exception) {
//            log.error("사용자 등록 - 거래처 정보 지정 중 에러발생!")
//            ignore.printStackTrace()
//        }
//
//        userRepository.save(user)
//        return UserResponse.of(user)
//    }

    fun getUserById(id: Long): User {
        return userRepository.findByIdOrThrow(id, "사용자를 찾을 수 없습니다.")
    }

    fun getMyInfo(): UserResponse {
        return UserResponse.of(getAccountFromSecurityContext())
    }

    fun updateUser(userUpdateRequest: UserUpdateRequest): UserResponse {
        val loginUser = getAccountFromSecurityContext()

        val user = getUserById(userUpdateRequest.id)

//        val laundryId = loginUser.getLaundry()?.id
//        val clientId = loginUser.getClient()?.id
//        val departmentId = loginUser.getDepartment()?.id
//        if (!loginUser.beAbleRole(laundryId, clientId, departmentId)) throw ForbiddenException()

        user.updateInfo(userUpdateRequest)

        userRepository.save(user)

        user.updatedBy(loginUser.username)

        return UserResponse.of(user)
    }

    @Lock(value = LockModeType.PESSIMISTIC_WRITE) // 읽기 쓰기 모두 불가능하도록 LOCK 걸기
    fun deleteUser(id: Long): Boolean {
        val user = getUserById(id)

        val loginUser = getAccountFromSecurityContext()

        user.softDelete(loginUser.username)

        userRepository.save(user)

        return user.isDeleted()
    }

    fun changePassword(putPasswordRequest: PutPasswordRequest): Boolean {
        val loginUser = getAccountFromSecurityContext()

        val user = getUserByUsername(putPasswordRequest.username)

//        val laundryId = loginUser.getLaundry()?.id
//        val clientId = loginUser.getClient()?.id
//        val departmentId = loginUser.getDepartment()?.id
//        if (!loginUser.beAbleRole(laundryId, clientId, departmentId)) throw ForbiddenException()

        val encryptPassword = passwordEncoder.encode(putPasswordRequest.password)
        user.changePassword(encryptPassword)

        userRepository.save(user)

        return true
    }

    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)

        fun getAccountFromSecurityContext(): User {
            val authentication = SecurityContextHolder.getContext().authentication
            log.info("authentication : $authentication")
            val principal = authentication.principal
            log.info("principal : $principal")
            if (principal == "anonymousUser") throw UnauthorizedException("계정이 확인되지 않습니다. 다시 로그인 해주세요.")
            return principal as User
        }
    }
}