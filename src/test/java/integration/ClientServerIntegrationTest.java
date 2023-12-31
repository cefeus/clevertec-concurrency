package integration;

import client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientServerIntegrationTest {

    private final int DATA_QUANTITY = 20;
    private final int THREADS_QUANTITY = 10;
    private Client client;
    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server();
        client = new Client(THREADS_QUANTITY, DATA_QUANTITY, server);
    }


    @Test
    public void clientShouldHaveCorrectAccumulatorSum() {
        Integer expected = (1 + DATA_QUANTITY) * (DATA_QUANTITY / 2);

        Integer actual = client.request();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void clientIntegerList_ShouldBeEmpty_WhenAllRequestsSent() {
        Integer expected = 0;

        client.request();
        Integer actual = client.getIntSource().size();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void serverShouldHaveAllValues_WithoutBlanks() {
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < DATA_QUANTITY; i++) {
            expected.add(i);
        }
        client.request();

        List<Integer> actual = server.getReceived();

        assertThat(actual)
                .containsAll(expected);
    }

}
