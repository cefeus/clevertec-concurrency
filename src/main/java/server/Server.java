package server;

import entity.Request;
import entity.Response;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    @Getter
    private final List<Integer> received = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    public Response answer(Request request) {
        lock.lock();
        received.add(request.getRequestValue());
        Response response = new Response(received.size());
        lock.unlock();
        sleepRandom(100, 1000);
        return response;
    }

    private void sleepRandom(int min, int max) {
        try {
            Thread.sleep(new Random().nextInt(max-min + 1) + min);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
