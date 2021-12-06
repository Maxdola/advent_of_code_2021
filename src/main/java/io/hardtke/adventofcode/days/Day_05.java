package io.hardtke.adventofcode.days;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day_05 extends Day {

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

  private static enum Type {
    POINT, DX, DY, DXY;
  }

  private static class Line {

    private int x1, x2;
    private int y1, y2;

    public Line(String rawLine) {
      String[] parts = rawLine.split(" -> ");
      String[] p1 = parts[0].split(",");
      String[] p2 = parts[1].split(",");

      x1 = Integer.parseInt(p1[0]);
      y1 = Integer.parseInt(p1[1]);

      x2 = Integer.parseInt(p2[0]);
      y2 = Integer.parseInt(p2[1]);
    }

    public boolean isVertical() {
      if (this.x1 == this.x2) return true;
      return this.y1 == this.y2;
    }

    public Type determineType() {
      int dx = Math.abs(x1-x2);
      int dy = Math.abs(y1-y2);

      if (dx == 0 && dy == 0) return Type.POINT;
      else if (dx == 0) return Type.DY;
      else if (dy == 0) return Type.DX;
      else if (dx == dy) return Type.DXY;
      else return null;
    }

    public List<Point> getPoints() {
      Type type = this.determineType();

      if (type == null) return Collections.emptyList();
      if (type == Type.POINT) return Collections.singletonList(new Point(this.x1, this.y1));

      List<Point> points = new ArrayList<>();

      switch (type) {
        case DX -> {
          int max = Math.max(this.x1, this.x2);
          int min = Math.min(this.x1, this.x2);
          for (int i = min; i <= max; i++) {
            points.add(new Point(i, this.y1));
          }
        }
        case DY -> {
          System.out.println("DY");
          System.out.println(this);
          int max = Math.max(this.y1, this.y2);
          int min = Math.min(this.y1, this.y2);
          for (int i = min; i <= max; i++) {
            points.add(new Point(this.x1, i));
          }
          System.out.println(points.size());
        }
        case DXY -> {
          int dx = this.x1 - this.x2;
          int dy = this.y1 - this.y2;
          if (dx == dy) {
            int max = Math.max(this.x1, this.x2);
            int min = Math.min(this.x1, this.x2);
            int miny = Math.min(this.y1, this.y2);
            for (int i = min; i <= max; i++) {
              points.add(new Point(i, miny + (i - min)));
            }
          } else {
            int max = Math.max(this.x1, this.x2);
            int min = Math.min(this.x1, this.x2);
            int maxy = Math.max(this.y1, this.y2);
            for (int i = min; i <= max; i++) {
              points.add(new Point(i, maxy - (i - min)));
            }
          }
        }
      }

      return points;
    }

    @Override
    public String toString() {
      return String.format("(%4s|%4s) -> (%4s|%4s)", x1, y1, x2, y2);
    }
  }

  @Override
  public void calculate() {

    List<Line> lines = new ArrayList<>();

    this.getLines().forEach(l -> lines.add(new Line(l)));

   /* lines.forEach(l -> {
      System.out.println(l);
      l.getPoints().forEach(System.out::println);
      System.out.println();
    });*/

    HashMap<Point, Integer> field = new HashMap<>();

    lines.stream().filter(Line::isVertical).forEach(l -> {
      System.out.println(l);
      l.getPoints().forEach(p -> {
        int i = field.getOrDefault(p, 0);
        i++;
        field.put(p, i);
      });
    });

    AtomicInteger a = new AtomicInteger(0);

    field.forEach((p, i) -> {
      if (i > 1) a.getAndIncrement();
      System.out.printf("%s = %d %n", p, i);
    });

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        int k = field.getOrDefault(new Point(j, i), 0);
        System.out.print(k == 0 ? "." : k);
      }
      System.out.println();
    }

    System.out.println("The solution for the first puzzle is:" +  a.get());

    calc2(lines);

  }

  private void calc2(List<Line> lines) {
    HashMap<Point, Integer> field = new HashMap<>();

    lines.stream().forEach(l -> {
      System.out.println(l);
      l.getPoints().forEach(p -> {
        int i = field.getOrDefault(p, 0);
        i++;
        field.put(p, i);
      });
    });

    AtomicInteger a = new AtomicInteger(0);

    field.forEach((p, i) -> {
      if (i > 1) a.getAndIncrement();
      System.out.printf("%s = %d %n", p, i);
    });

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        int k = field.getOrDefault(new Point(j, i), 0);
        System.out.print(k == 0 ? "." : k);
      }
      System.out.println();
    }

    System.out.println("The solution for the second puzzle is:" +  a.get());
  }

}
