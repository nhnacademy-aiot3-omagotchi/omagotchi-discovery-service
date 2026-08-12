package site.omagotchi.discoveryservice;

import com.netflix.eureka.EurekaServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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

}
