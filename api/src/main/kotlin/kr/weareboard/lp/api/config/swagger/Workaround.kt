package kr.weareboard.lp.api.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.stereotype.Component
import springfox.documentation.oas.web.OpenApiTransformationContext
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter
import springfox.documentation.spi.DocumentationType
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class Workaround : WebMvcOpenApiTransformationFilter {
    override fun transform(context: OpenApiTransformationContext<HttpServletRequest>): OpenAPI {
        val openApi = context.specification
        val localServer = Server()
        localServer.description = "로컬 서버"
        localServer.url = "http://localhost:53101"
        val prodServer = Server()
        prodServer.description = "운영 서버"
        prodServer.url = "https://lp.weareboard.kr"
        openApi.servers = listOf(prodServer, localServer)
        return openApi
    }

    override fun supports(documentationType: DocumentationType): Boolean {
        return documentationType == DocumentationType.OAS_30
    }
}