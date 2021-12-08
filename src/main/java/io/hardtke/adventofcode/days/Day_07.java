package io.hardtke.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day_07 extends Day {

  @Override
  public void calculate() {

    List<Long> subs = Arrays.stream(getLines().get(0).split(",")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

    long min = subs.stream().mapToLong(Long::valueOf).min().getAsLong();
    long max = subs.stream().mapToLong(Long::valueOf).max().getAsLong();

    long cheapestI = -1;
    long cost = Long.MAX_VALUE;

    for (long i = min; i <= max; i++) {
      long s  = calcCost(subs, i);
      if (s < cost) {
        cheapestI = i;
        cost = s;
      }
    }

    System.out.printf("Cheapest %d -> %d %n", cheapestI, cost);

    cheapestI = -1;
    cost = Integer.MAX_VALUE;


    for (long i = min; i <= max; i++) {
      long s  = calcCostV2(subs, i);
      if (s < cost) {
        cheapestI = i;
        cost = s;
      }
    }

    System.out.printf("Cheapest %d -> %d %n", cheapestI, cost);

  }

  private long calcCost(List<Long> subs, long i) {
    return subs.stream().map(s -> Math.abs(s - i)).mapToLong(Long::valueOf).sum();
  }

  private long calcCostV2(List<Long> subs, long i) {
    return subs.stream().map(s -> sumN(Math.abs(s - i))).mapToLong(Long::valueOf).sum();
  }

  private long sumN(long n) {
    long s = 0;
    for (long i = 0; i <= n; i++) {
      s += i;
    }
    return s;
  }

}
