package io.hardtke.adventofcode.days;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day_06 extends Day {

  private static class Point {
    private int x,y;

    private Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Point)) return false;
      Point point = (Point) o;
      return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public String toString() {
      return String.format("(%4s|%4s)", x, y);
    }
  }

  @Override
  public void calculate() {

    String raw = this.getLines().get(0);

    List<Long> initialFish = Arrays.stream(raw.split(",")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

    Map<Integer, Long> initialFishMap = new HashMap<>();

    for (int i = 0; i < 10; i++) {
      int finalI = i;
      long count = (initialFish.stream().filter(k -> k == finalI).count());
      initialFishMap.put(i, count);
    }

    Map<Integer, Long> currentFish = new HashMap<>(initialFishMap);
    Map<Integer, Long> newFish = new HashMap<>();

    for (int i = 0; i < 80; i++) {
      for (int j = 1; j < 10; j++) {
        if (!currentFish.containsKey(j)) continue;
        long count = currentFish.get(j);
        newFish.put(j - 1, count);
      }
      long zero = currentFish.get(0);
      newFish.compute(6, (k, v) -> v == null ? zero : v + zero);
      newFish.put(8, zero);

      currentFish.clear();
      currentFish.putAll(newFish);
      newFish.clear();
    }

    System.out.printf("Total fish 80d: %d%n", currentFish.values().stream().mapToLong(Long::valueOf).sum());

    currentFish.clear();
    currentFish.putAll(initialFishMap);

    for (int i = 0; i < 256; i++) {
      for (int j = 1; j < 10; j++) {
        if (!currentFish.containsKey(j)) continue;
        long count = currentFish.get(j);
        newFish.put(j - 1, count);
      }
      long zero = currentFish.get(0);
      newFish.compute(6, (k, v) -> v == null ? zero : v + zero);
      newFish.put(8, zero);

      currentFish.clear();
      currentFish.putAll(newFish);
      newFish.clear();
    }

    System.out.printf("Total fish 256d: %d%n", currentFish.values().stream().mapToLong(Long::valueOf).sum());


  }

}
