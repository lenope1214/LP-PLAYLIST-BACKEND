package kr.weareboard.lp.domain.entity.user

import kr.weareboard.lp.domain.entity.user.enum.UserRoleType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class UserOAuth2Service(
    private val userService: UserDetailServiceImpl,
) : DefaultOAuth2UserService() {
//    private val httpSession: HttpSession? = null
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        log.info("load user")
        val oAuth2User = super.loadUser(userRequest)
        val attributes = oAuth2User.attributes
        val kakao_account = attributes["kakao_account"] as Map<String, Any>?
        val email = kakao_account!!["email"] as String
        val properties = attributes["properties"] as Map<String, Any>?
        val nickname = properties!!["nickname"] as String
        if (userService.isNonExistEmail(email)) {
            userService.createMemberKakao(email, nickname)
        } else {
            log.info("이미 존재하는 이메일입니다.")
        }
        return DefaultOAuth2User(setOf(SimpleGrantedAuthority(UserRoleType.ROLE_USER.name)), attributes, "id")
    }
}