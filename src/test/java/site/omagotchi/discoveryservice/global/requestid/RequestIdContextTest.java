package site.omagotchi.discoveryservice.global.requestid;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.assertj.core.api.BDDAssertions.then;

class RequestIdContextTest {

    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    @Test
    @DisplayName("새 작업 범위의 Request ID 생성과 바깥 값 복원")
    void createsRequestIdAndRestoresOuterContext() {
        // Given
        String outerRequestId = "0123456789abcdef0123456789abcdef";
        MDC.put(RequestIdContext.MDC_KEY, outerRequestId);

        // When
        try (RequestIdContext.Scope ignored = RequestIdContext.openNew()) {
            // Then
            then(RequestIdContext.currentValue())
                    .matches("^[0-9a-f]{32}$")
                    .isNotEqualTo(outerRequestId);
        }

        then(RequestIdContext.currentValue()).isEqualTo(outerRequestId);
    }

    @Test
    @DisplayName("HTTP 요청 범위 종료 후 Request ID 제거")
    void removesInboundRequestIdAfterScopeCloses() {
        // Given
        RequestId requestId = new RequestId("abcdef0123456789abcdef0123456789");

        // When
        try (RequestIdContext.Scope ignored = RequestIdContext.openInbound(requestId)) {
            // Then
            then(RequestIdContext.currentValue()).isEqualTo(requestId.value());
        }

        then(RequestIdContext.currentValue()).isNull();
    }
}
