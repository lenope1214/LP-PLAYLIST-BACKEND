package kr.weareboard.lp.domain.entity.lp

import kr.weareboard.lp.domain.entity.BaseEntity
import kr.weareboard.lp.domain.entity.user.User
import org.hibernate.annotations.Comment
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "deleted_at IS NULL")
@Entity
@Table(name = "tb_lp")
class Lp(

    @Comment(value = "제목")
    @Column(name = "title", length = 100, nullable = false)
    var title: String,

    @Comment(value = "가수")
    @Column(name = "singer", length = 50, nullable = false)
    var singer: String,

    @Comment(value = "기본키")
    @Column(name = "message", length = 500, nullable = false)
    var message: String,

    @Comment(value = "기본키")
    @Column(name = "url")
    var url: String = "",


    @Comment(value = "randomCoverPath (client image path)")
    @Column(name = "random_cover_path")
    var randomCoverPath: String? = null,

    @Comment(value = "cover image path")
    @Column(name = "img_path")
    var imgPath: String? = null,

    @Comment(value = "thumbnail image path")
    @Column(name = "thumbnail_image_path")
    var thumbnailImgPath: String? = null,

    @Comment(value = "기본키")
    @Column(name = "is_read")
    var isRead: Boolean = false,

    @Comment(value = "기본키")
    @Column(name = "writer_nickname")
    var writerNickname: String,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH, CascadeType.MERGE]
    )
    @JoinColumn(
        name = "writer_id",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @Comment(value = "작성자 정보")
    var writer: User? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH, CascadeType.MERGE]
    )
    @JoinColumn(
        name = "receiver_id",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @Comment(value = "작성자 정보")
    var receiver: User? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "기본키")
    @Column(name = "lp_panel_id")
    val id: Long? = null,
) : BaseEntity() {

    fun update(title: String?, singer: String?, message: String?, url: String?, thumbnailImgPath: String?) {
        this.title = title ?: ""
        this.singer = singer ?: ""
        this.message = message ?: ""
        this.url = url ?: ""
        this.thumbnailImgPath = thumbnailImgPath ?: ""
    }

    fun updateImgPath(imgPath: String?, randomCoverPath: String?){
        this.imgPath = imgPath
        this.randomCoverPath = randomCoverPath
    }

    fun updateRead(read: Boolean) {
        this.isRead = read
    }

    fun updateReceiver(receiver: User) {
        this.receiver = receiver
    }

    fun updateWriter(writer: User) {
        this.writer = writer
    }

}