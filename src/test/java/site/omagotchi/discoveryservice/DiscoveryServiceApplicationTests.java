package site.omagotchi.discoveryservice;

import com.netflix.eureka.EurekaServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class DiscoveryServiceApplicationTests {

    @Autowired
    private EurekaServerConfig eurekaServerConfig;

    @Test
    void contextLoads() {
    }

    @Test
    void disablesReadOnlyResponseCache() {
        assertThat(this.eurekaServerConfig.shouldUseReadOnlyResponseCache()).isFalse();
    }

    @Test
    void delegatesStartupToSpringApplication() {
        String[] args = {"--server.port=0"};

        try (var springApplication = mockStatic(SpringApplication.class)) {
            DiscoveryServiceApplication.main(args);

            springApplication.verify(
                    () -> SpringApplication.run(DiscoveryServiceApplication.class, args)
            );
        }
    }

}
