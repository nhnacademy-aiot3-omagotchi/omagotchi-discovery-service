package site.omagotchi.discoveryservice.global.logging;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationPredicate;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.stereotype.Component;

/** Registry 갱신·조회와 관리 Endpoint의 반복 HTTP 계측 제외, JVM 메트릭 유지. */
@Component
public class DiscoveryObservationPredicate implements ObservationPredicate {

    @Override
    public boolean test(String name, Observation.Context context) {
        if (!(context instanceof ServerRequestObservationContext requestContext)) {
            return true;
        }

        String path = requestContext.getCarrier().getRequestURI();
        return !path.equals("/eureka") && !path.startsWith("/eureka/")
                && !path.equals("/actuator") && !path.startsWith("/actuator/");
    }
}
