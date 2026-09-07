package site.omagotchi.discoveryservice.global.logging;

import io.micrometer.observation.Observation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Discovery 반복 HTTP 계측 제외")
class DiscoveryObservationPredicateTest {

    @ParameterizedTest
    @CsvSource({"/eureka,false", "/eureka/apps/IDENTITY/instance,false",
            "/actuator/health,false", "/actuator/prometheus,false", "/other,true"})
    @DisplayName("Registry·관리 요청만 제외하고 다른 작업의 계측 유지")
    void excludesOnlyRegistryAndManagementRequests(String path, boolean expected) {
        // Given
        var predicate = new DiscoveryObservationPredicate();
        var context = new ServerRequestObservationContext(
                new MockHttpServletRequest("GET", path), new MockHttpServletResponse());

        // When
        boolean observed = predicate.test("http.server.requests", context);

        // Then
        assertThat(observed).isEqualTo(expected);
        assertThat(predicate.test("jvm", new Observation.Context())).isTrue();
    }
}
