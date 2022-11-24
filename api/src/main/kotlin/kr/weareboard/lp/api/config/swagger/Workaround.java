package kr.weareboard.lp.api.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class Workaround implements WebMvcOpenApiTransformationFilter {

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openApi = context.getSpecification();
        Server localServer = new Server();
        localServer.setDescription("로컬 서버");
        localServer.setUrl("http://localhost:3100");

        Server prodServer = new Server();
        prodServer.setDescription("운영 서버");
        prodServer.setUrl("http://laundry.j-sol.co.kr");
        openApi.setServers(Arrays.asList(prodServer, localServer));
        return openApi;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }
}