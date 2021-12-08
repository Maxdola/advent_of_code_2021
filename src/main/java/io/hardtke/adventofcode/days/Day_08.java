package io.hardtke.adventofcode.days;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day_08 extends Day {

  @Override
  public void calculate() {

    AtomicInteger i = new AtomicInteger();
    AtomicInteger i2 = new AtomicInteger();

    this.getLines().forEach(l -> {

      String[] parts = l.split(" \\| ");
      int count = 0;
      for (String s : parts[1].split(" ")) {
        if (s.length() == 2 || s.length() == 4 || s.length() == 3 || s.length() == 7) count++;
      }
      i.getAndAdd(count);

      i2.addAndGet(solve(parts[0], parts[1]));
    });

    System.out.printf("Question 1: %d %n", i.get());
    System.out.printf("Question 2: %d %n", i2.get());

  }

  private int solve(String input, String output) {
    String[] inputs = input.split(" ");
    String[] outputs = output.split(" ");

    Map<String, Integer> translation = new HashMap<>();

    String one = findByLength(inputs, 2);
    String four = findByLength(inputs, 4);
    String seven = findByLength(inputs, 3);
    String eight = findByLength(inputs, 7);

    translation.put(one, 1);
    translation.put(four, 4);
    translation.put(seven, 7);
    translation.put(eight, 8);

    char tl = '?';
    char bl = '?';

    var l = findManyByLength(inputs, 6);

    var l1 = l.get(0);
    var l2 = l.get(1);
    var l3 = l.get(2);

    String zero = "";
    String six = "";
    String nine = "";

    if (containsAll(l1, four)) {
      nine = l1;
      zero = containsAll(l2, one) ? l2 : l3;
      six = !containsAll(l2, one) ? l2 : l3;
    } else if (containsAll(l2, four)) {
      nine = l2;
      zero = containsAll(l1, one) ? l1 : l3;
      six = !containsAll(l1, one) ? l1 : l3;
    } else {
      nine = l3;
      zero = containsAll(l2, one) ? l2 : l1;
      six = !containsAll(l2, one) ? l2 : l1;
    }

    translation.put(nine, 9);
    translation.put(six, 6);
    translation.put(zero, 0);

    tl = notIncluded(one, six).get(0);
    bl = notIncluded(one, String.valueOf(tl)).get(0);


    for (String k : findManyByLength(inputs, 5)) {
      if (containsAll(k, one)) {
        translation.put(k, 3);
      } else if (containsAll(k, String.valueOf(tl))) {
        translation.put(k, 2);
      } else if (containsAll(k, String.valueOf(bl))) {
        translation.put(k, 5);
      }
    }

    StringBuilder sb = new StringBuilder();
    for (String out : outputs) {
      translation.forEach((k,v) -> {
        if (compare(k, out)) sb.append(v);
      });
    }

    return Integer.parseInt(sb.toString());

  }

  private String findByLength(String[] values, int length) {
    for (String value : values) {
      if (value.length() == length) return value;
    }
    return null;
  }

  private List<String> findManyByLength(String[] values, int length) {
    List<String> rets = new ArrayList<>();
    for (String value : values) {
      if (value.length() == length) rets.add(value);
    }
    return rets;
  }

  private boolean containsAll(String source, String query) {
    return source.chars().mapToObj(e->(char)e).collect(Collectors.toList()).containsAll(query.chars().mapToObj(e->(char)e).collect(Collectors.toList()));
  }

  private List<Character> notIncluded(String source, String query) {
    List<Character> chars = source.chars().mapToObj(e->(char)e).collect(Collectors.toList());
    chars.removeAll(query.chars().mapToObj(e->(char)e).collect(Collectors.toList()));
    return chars;
  }

  private boolean compare(String s, String d) {
    List<String> sc = s.chars().mapToObj(String::valueOf).collect(Collectors.toList());
    List<String> dc = d.chars().mapToObj(String::valueOf).collect(Collectors.toList());

    Collections.sort(sc);
    Collections.sort(dc);

    return String.join("", sc).equals( String.join("", dc));
  }

}
