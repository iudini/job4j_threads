package ru.job4j.exam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Aggregator {
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final String url;

    public Aggregator(String url) {
        this.url = url;
    }

    private static CompletableFuture<String> getData(String url) {
        return CompletableFuture.supplyAsync(() -> {
            StringBuilder builder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
                br.lines().forEach(builder::append);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        });
    }

    private static Camera parse(JSONObject jsonObject) throws ExecutionException, InterruptedException {
        JSONObject sourceDataUrl = new JSONObject(getData(jsonObject.getString("sourceDataUrl")).get());
        JSONObject tokenDataUrl = new JSONObject(getData(jsonObject.getString("tokenDataUrl")).get());
        int id = jsonObject.getInt("id");
        String urlType = sourceDataUrl.optString("urlType");
        String videoUrl = sourceDataUrl.optString("videoUrl");
        String value = tokenDataUrl.getString("value");
        int ttl = tokenDataUrl.getInt("ttl");
        return new Camera(id, urlType, videoUrl, value, ttl);
    }

    public List<Camera> aggregate() throws ExecutionException, InterruptedException {
        List<FutureTask<Camera>> tasks = new CopyOnWriteArrayList<>();
        List<Camera> cameraList = new ArrayList<>();
        String data = getData(url).get();
        JSONArray cameras = new JSONArray(data);
        for (var camera : cameras) {
            FutureTask<Camera> task = new FutureTask<>(() -> parse((JSONObject) camera));
            tasks.add(task);
            pool.submit(task);
        }
        for (var task : tasks) {
            cameraList.add(task.get());
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return cameraList;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Aggregator aggregator = new Aggregator("http://www.mocky.io/v2/5c51b9dd3400003252129fb5");
        List<Camera> cameraList = aggregator.aggregate();
        for (var cam : cameraList) {
            System.out.println(cam);
        }
    }
}
