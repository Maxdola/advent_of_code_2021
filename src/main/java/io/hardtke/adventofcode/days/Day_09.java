package io.hardtke.adventofcode.days;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day_09 extends Day {

  @Override
  public void calculate() {

    Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

    this.getLines().forEach(l -> {
      Map<Integer, Integer> row = new HashMap<>();
      for (String raw : l.split("")) {
        row.put(row.size(), Integer.parseInt(raw));
      }
      map.put(map.size(), row);
    });

    List<Integer> lowPoints = new ArrayList<>();

    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).size(); j++) {

        int p = getPoint(map, i, j);
        int p1 = getPoint(map, i-1, j);
        int p2 = getPoint(map, i+1, j);
        int p3 = getPoint(map, i, j-1);
        int p4 = getPoint(map, i, j+1);

        if (p < p1 && p < p2 && p < p3 && p < p4) {
          lowPoints.add(p);
        }
      }
    }

    System.out.println(lowPoints.stream().map(String::valueOf).collect(Collectors.joining(" ")));

    System.out.printf("Question 1: %d %n", lowPoints.stream().mapToInt(Integer::valueOf).sum() + lowPoints.size());

    List<Integer> besin = new ArrayList<>();

    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).size(); j++) {

        int p = getPoint(map, i, j);
        int p1 = getPoint(map, i-1, j);
        int p2 = getPoint(map, i+1, j);
        int p3 = getPoint(map, i, j-1);
        int p4 = getPoint(map, i, j+1);

        if (p < p1 && p < p2 && p < p3 && p < p4) {
          int b = findBasins(map, i, j);
          besin.add(b);
          System.out.printf("Basin at: (%2d|%2d): %3d %n", i, j, b);
        }
      }
    }

    Collections.sort(besin);
    Collections.reverse(besin);

    int f = besin.get(0) * besin.get(1) * besin.get(2);

    System.out.printf("B1: %3d B2: %3d B3: %3d%n", besin.get(0), besin.get(1), besin.get(2));

    System.out.printf("Question 2: %d %n", f);

  }

  private int getPoint(Map<Integer, Map<Integer, Integer>> map, int x, int y) {

    if (x < 0) return Integer.MAX_VALUE;
    if (y < 0) return Integer.MAX_VALUE;
    if (x >= map.size()) return Integer.MAX_VALUE;
    if (y >= map.get(0).size()) return Integer.MAX_VALUE;

    return map.get(x).get(y);

  }

  private List<Point> getSurroundingPoints(Map<Integer, Map<Integer, Integer>> map, int x, int y) {
    List<Point> points = new ArrayList<>();
    int p = getPoint(map, x, y);
    int p1 = getPoint(map, x-1, y);
    int p2 = getPoint(map, x+1, y);
    int p3 = getPoint(map, x, y-1);
    int p4 = getPoint(map, x, y+1);

    //System.out.printf("P0: %d, P1: %d, P2: %d, P3: %d, P4: %d %n", p, p1, p2, p3, p4);

    if (p1 < 9 && p1 > p) points.add(new Point(x-1, y));
    if (p2 < 9 && p2 > p) points.add(new Point(x+1, y));
    if (p3 < 9 && p3 > p) points.add(new Point(x, y-1));
    if (p4 < 9 && p4 > p) points.add(new Point(x, y+1));

    return points;
  }

  private int findBasins(Map<Integer, Map<Integer, Integer>> map, int x, int y) {

    Set<Point> points = new HashSet<>(getSurroundingPoints(map, x, y));
    points.add(new Point(x, y));
    Set<Point> newPoints = new HashSet<>(getSurroundingPoints(map, x, y));

    while (newPoints.size() > 0) {
      Set<Point> tempPoints = new HashSet<>();
      for (Point point : newPoints) {
        tempPoints.addAll(getSurroundingPoints(map, point.x, point.y));
      }
      newPoints.clear();
      points.addAll(tempPoints);
      newPoints.addAll(tempPoints);
    }

    return points.size();

  }

}
