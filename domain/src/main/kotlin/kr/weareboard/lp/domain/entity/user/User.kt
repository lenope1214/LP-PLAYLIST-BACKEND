package kr.weareboard.lp.domain.entity.user

import kr.weareboard.lp.domain.entity.BaseEntity
import kr.weareboard.lp.domain.entity.user.dto.request.UserUpdateRequest
import kr.weareboard.lp.domain.entity.user.enum.OAuth2Provider
import kr.weareboard.lp.domain.entity.user.enum.UserRoleType
import org.hibernate.annotations.Comment
import org.hibernate.annotations.Where
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@Entity
@Table(name = "tb_user")
class User(

    @Column(name = "username", length = 20, nullable = false)
    @Comment(value = "사용자 로그인 아이디")
    private val username: String,

    @Column(name = "password", length = 255, nullable = false)
    @Comment(value = "사용자 로그인 비밀번호")
    private var password: String,

    @Column(name = "name", length = 50, nullable = false)
    @Comment(value = "사용자 이름(성함)")
    var name: String,

    @Column(name = "email", length = 255, nullable = false)
    @Comment(value = "사용자 이메일")
    var email: String,

    @Column(name = "provider", length = 255, nullable = false)
    @Comment(value = "사용자 oauth2 인증소")
    val provider: OAuth2Provider = OAuth2Provider.PLZ,

    @Column(name = "role", length = 50, nullable = false)
    @Comment(value = "사용자 권한(ROLE_XXX)")
    @Enumerated(EnumType.STRING)
    val role: UserRoleType = UserRoleType.ROLE_USER,

    @Column(name = "changePassword", length = 50, nullable = false)
    @Comment(value = "사용자 비밀번호 변경 유무")
    val changePassword: Boolean = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "기본키")
    @Column(name = "user_id")
    val id: Long? = null,
) : UserDetails, BaseEntity() {

    var kakaoToken: String = ""
    
    private var loginAt: LocalDateTime? = null

    fun updateInfo(userUpdateRequest: UserUpdateRequest) {
        this.name = userUpdateRequest.name ?: this.name
    }


    fun login() {
        loginAt = LocalDateTime.now()
    }

    fun checkEmail(): Boolean {
        return email.isNotEmpty()
    }

    fun isChangePassword(): Boolean = this.changePassword

    override fun getUsername(): String = this.username

    override fun getPassword(): String = this.password

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(this.role.name))
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun changePassword(encryptPassword: String) {
        this.password = encryptPassword
    }

    override
    fun toString(): String{
        return "User(id=$id, username='$username', password='$password', name='$name', role=$role, changePassword=$changePassword, loginAt=$loginAt)"
    }

    fun toRefererString(): String{
        return "User(id=$id, username='$username', password='$password', name='$name', role=$role)"
    }
}