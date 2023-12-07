package client;

import entity.Request;
import entity.Response;
import server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private List<Integer> intSource;
    private final Lock lock = new ReentrantLock();
    private final int dataQuantity;
    private Integer accumulator;
    private final Server server = new Server();
    private final Random rand = new Random();

    private final ExecutorService threads;

    public Client(int threadCount, int dataQuantity) {
        this.dataQuantity = dataQuantity;
        accumulator = 0;
        intSource = fillArray(dataQuantity);
        threads = Executors.newFixedThreadPool(threadCount);
    }

    public Integer request() {
        List<Callable<Response>> tasks = new ArrayList<>();
        for (int i = 0; i < dataQuantity; i++) {
            Integer value = intSource.remove(getRandomInt(intSource.size()));
            tasks.add(() -> server.answer(new Request(value)));
        }
        try {
            List<Future<Response>> futureResponses = threads.invokeAll(tasks);
            futureResponses.stream().forEach(this::addToAccumulator);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            threads.shutdown();
        }
        return accumulator;
    }

    private void addToAccumulator(Future<Response> response) {
        try {
            Integer value = response.get().getResponseValue();
            System.out.println(value);
            lock.lock();
            accumulator += value;
            lock.unlock();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private int getRandomInt(int n) {
        return rand.nextInt(n);
    }

    private List<Integer> fillArray(int dataQuantity) {
        List<Integer> buff = new ArrayList<>();
        for(int i = 0; i < dataQuantity; i++) {
            buff.add(i);
        }
        return buff;
    }
}
