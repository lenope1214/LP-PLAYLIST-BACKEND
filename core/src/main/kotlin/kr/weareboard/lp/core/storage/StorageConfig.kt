//package kr.weareboard.lp.core.storage
//
//import org.springframework.boot.context.properties.ConfigurationProperties
//import org.springframework.context.annotation.Configuration
//
//// application.yml 혹은 properties에
//// storage.location : ~~~
//// storage:
////  location: ~~~ 와 같은 형태로 값이 있어야 함.
//@ConfigurationProperties(prefix = "storage")
//@Configuration  // 이게 없으면 또 오류나네.. 전에는 잘만 됐는데
//class StorageConfig {
//    var location: String? = null
//}