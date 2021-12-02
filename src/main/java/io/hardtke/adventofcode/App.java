package io.hardtke.adventofcode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.hardtke.adventofcode.days.*;

public class App {

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        //new Day_01();
        new Day_02();

        System.out.printf("Time: %dms", System.currentTimeMillis() - startTime);

    }
}
