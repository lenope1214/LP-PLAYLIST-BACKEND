package kr.weareboard.lp.core.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebStorageConfig : WebMvcConfigurer {

    @Value("\${storage.prefix}")
    private val prefix: String? = null

    @Value("\${storage.filePath}")
    private val location: String? = null

    // 정적 파일 반환 설정
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        println("LOCATION = ${location}")
        registry.addResourceHandler("/${prefix}/**")
            .addResourceLocations(location) // .addResourceLocations("file:////Users/jo/dev/jsol/RE-KNUH/video/")
            .setCachePeriod(20)
    }
}