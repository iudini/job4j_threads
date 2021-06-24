package ru.job4j.exam;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AggregatorTest {

    @Test
    public void aggregate() throws ExecutionException, InterruptedException {
        Aggregator aggregator = new Aggregator("http://www.mocky.io/v2/5c51b9dd3400003252129fb5");
        List<Camera> cameraList = aggregator.aggregate();
        assertThat(cameraList.get(0), is(new Camera(1,
                "LIVE",
                "rtsp://127.0.0.1/1",
                "fa4b588e-249b-11e9-ab14-d663bd873d93",
                120)));
    }
}