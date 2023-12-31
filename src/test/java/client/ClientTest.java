package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import static org.assertj.core.api.Assertions.assertThat;


class ClientTest {

    private final int DATA_QUANTITY = 20;
    private final int THREADS_QUANTITY = 10;

    private Server server;
    private Client client;

    @BeforeEach
    void setUp() {
        server = new Server();
        client = new Client(THREADS_QUANTITY, DATA_QUANTITY, server);
    }

    @Test
    public void request_IntSourceSizeShouldBeZero_WhenRequestsSent() {
        Integer expected = 0;
        client.request();
        Integer actual = client.getIntSource().size();

        assertThat(actual)
                .isEqualTo(expected);
    }

}