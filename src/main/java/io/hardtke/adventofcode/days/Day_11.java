package io.hardtke.adventofcode.days;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day_11 extends Day {

  @Override
  public void calculate() {

    Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

    AtomicInteger r = new AtomicInteger();

    this.getLines().forEach(l -> {
      Map<Integer, Integer> row = new HashMap<>();
      int i = 0;
      for (String s : l.split("")) {
        row.put(i, Integer.parseInt(s));
        i++;
      }
      map.put(r.getAndIncrement(), row);
    });

    int flashes = 0;
    for (int i = 0; i < 100; i++) {
      flashes += step(map);
      System.out.printf("Step %2d: (%3d)%n", i, flashes);
      printMap(map);
    }
    System.out.println(flashes);
    //printMap(map);

    boolean sync = true;
    int i = 1;
    while (step(map) != 100) {
      i++;
    }

    System.out.printf("Question 2: %d%n", i + 100);

  }

  private int step(Map<Integer, Map<Integer, Integer>> map) {
    //Map<Integer, Map<Integer, Integer>> tempMap = cloneMap(map);

    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).size(); j++) {
        map.get(i).put(j, map.get(i).get(j) + 1);
      }
    }

    int flashes = 0;
    boolean changes = true;
    while (changes) {
      int tc = 0;
      for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map.get(0).size(); j++) {
          int o = map.get(i).get(j);
          if (o > 9) {
            //System.out.printf("(%d|%d)%n", i,j);
            tc++;
            flashes++;
            flash(map, i, j);
          }
        }
      }
      if (tc == 0) changes = false;
    }

    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).size(); j++) {
        if (map.get(i).get(j) < 0) {
          map.get(i).put(j, 0);
        }
      }
    }

    return flashes;

  }

  private void flash(Map<Integer, Map<Integer, Integer>> map, int x, int y) {
    map.get(x).put(y, Integer.MIN_VALUE);
    incPoint(map, x - 1, y - 1);
    incPoint(map, x - 1, y - 0);
    incPoint(map, x - 1, y + 1);
    incPoint(map, x - 0, y + 1);
    incPoint(map, x + 1, y + 1);
    incPoint(map, x + 1, y - 0);
    incPoint(map, x + 1, y - 1);
    incPoint(map, x - 0, y - 1);
  }

  private void incPoint(Map<Integer, Map<Integer, Integer>> map, int x, int y) {
    if (x < 0) return;
    if (y < 0) return;
    int max = map.size();
    int may = map.get(0).size();
    if (x < max && y < may) {
      map.get(x).put(y, map.get(x).get(y) + 1);
    }
  }

  private Map<Integer, Map<Integer, Integer>> cloneMap(Map<Integer, Map<Integer, Integer>> map) {
    Map<Integer, Map<Integer, Integer>> newMap = new HashMap<>();
    for (int i = 0; i < map.size(); i++) {
      Map<Integer, Integer> row = new HashMap<>(map.get(i));
      newMap.put(i, row);
    }
    return newMap;
  }

  private void printMap(Map<Integer, Map<Integer, Integer>> map) {
    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).size(); j++) {
        System.out.print(map.get(i).get(j));
      }
      System.out.println();
    }
  }

}
