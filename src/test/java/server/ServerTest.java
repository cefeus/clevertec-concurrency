package server;

import entity.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    private final int DATA_QUANTITY = 20;
    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server();
    }

    @Test
    public void serverShouldHaveCorrectReceivedSize_afterBeingCalled() {
        for (int i = 0; i < DATA_QUANTITY; i++) {
            server.answer(new Request(i));
        }
        Integer actual = server.getReceived().size();

        assertThat(actual)
                .isEqualTo(DATA_QUANTITY);
    }

}