package io.hardtke.adventofcode.days;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day_03 extends Day {

  @Override
  public void calculate() {

    int lenght = this.getLines().get(0).length();

    List<Character> mostCommon = new LinkedList<>();

    for (int i = 0; i < lenght; i++) {
      int zeros = 0;
      int ones = 0;

      for (int j = 0; j < this.getLines().size(); j++) {

        String s = getLines().get(j);
        if (s.charAt(i) == '0') {
          zeros++;
        } else {
          ones++;
        }
      }

      if (zeros > ones) {
        mostCommon.add('0');
      } else {
        mostCommon.add('1');
      }

    }

    String max = "";
    String min = "";
    for (int i = 0; i < mostCommon.size(); i++) {
      if (mostCommon.get(i) == '0') {
        max += "0";
        min += "1";
      } else {
        max += "1";
        min += "0";
      }
    }
    System.out.printf("max: %s min: %s %n", max, min);
    System.out.printf("max: %d min: %d => %d %n", binaryToInt(max), binaryToInt(min), binaryToInt(max)* binaryToInt(min));

    StringBuilder a = new StringBuilder();
    StringBuilder b = new StringBuilder();
    calc2(0, a, b);

    System.out.printf("max: %s -> %d %n", a, binaryToInt(a.toString()));
    System.out.printf("min: %s -> %d %n", b, binaryToInt(b.toString()));
    System.out.printf("mult: %d %n", binaryToInt(b.toString()) * binaryToInt(a.toString()));

  }

  private void calc2(int pos, StringBuilder most, StringBuilder least) {
    int lenght = this.getLines().get(0).length();

    if (pos >= lenght) return;

    int least_zeros = 0;
    int most_zeros = 0;
    int least_ones = 0;
    int most_ones = 0;

    List<String> leastLines = this.getLines().stream().filter(s -> s.startsWith(least.toString())).collect(Collectors.toList());
    List<String> mostLines  = this.getLines().stream().filter(s -> s.startsWith(most.toString())).collect(Collectors.toList());

    if (leastLines.size() > 1) {
      for (int j = 0; j < leastLines.size(); j++) {

        String s = leastLines.get(j);
        if (s.charAt(pos) == '0') {
          least_zeros++;
        } else {
          least_ones++;
        }
      }

      if (least_zeros == least_ones || least_zeros < least_ones) {
        least.append("0");
        System.out.printf("%d:\t 1: %d \t0: %d\t -> 0 %n", pos, least_zeros, least_ones);
      } else {
        least.append("1");
        System.out.printf("%d:\t 1: %d \t0: %d\t -> 1 %n", pos, least_zeros, least_ones);
      }

    } else {
      System.out.println("Least: " + leastLines.get(0));
      least.setLength(0);
      least.append(leastLines.get(0));
    }

    if (mostLines.size() > 0) {

      for (int j = 0; j < mostLines.size(); j++) {

        String s = mostLines.get(j);
        if (s.charAt(pos) == '0') {
          most_zeros++;
        } else {
          most_ones++;
        }
      }

      if (most_zeros > most_ones) {
        most.append("0");
      } else {
        most.append("1");
      }
    } else {
      System.out.println("Most: " + mostLines.get(0));
      most.setLength(0);
      most.append(mostLines.get(0));
    }

    calc2(pos + 1, most, least);

  }

  private int binaryToInt(String binary) {
    int sum = 0;
    int f = 1;
    for (int i = binary.length() - 1; i >= 0; i--) {
      char c = binary.charAt(i);
      sum += c == '0' ? 0 : f;
      f *= 2;
    }
    return sum;
  }

}
