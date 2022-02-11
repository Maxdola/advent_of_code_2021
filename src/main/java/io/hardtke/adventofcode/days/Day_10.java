package io.hardtke.adventofcode.days;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Day_10 extends Day {

  @Override
  public void calculate() {

    int sum = this.getLines().stream().mapToInt(this::evaluateLine).sum();

    System.out.printf("Question 1: %d%n", sum);

    List<Long> autoScores = new ArrayList<>();

    this.getLines().forEach(l -> {
      if (evaluateLine(l) == 0) {
        autoScores.add(completeLine(l));
      }
    });

    Collections.sort(autoScores);

    System.out.println(autoScores.stream().map(String::valueOf).collect(Collectors.joining("   ")));

    System.out.printf("Question 2: %d%n", autoScores.get(autoScores.size() / 2));

  }

  private int evaluateLine(String line) {
    char[] chars = line.toCharArray();

    Stack<Character> stack = new Stack<>();

    for (char c : chars) {
      if (isOpening(c)) {
        stack.push(c);
      } else {
        char cc = stack.pop();
        if (!pair(cc, c)) {
          //System.out.printf("Expected %c, but found %c instead%n", cc, c);
          return getPoints(c);
        }
      }
    }

    return 0;
  }

  private long completeLine(String line) {
    char[] chars = line.toCharArray();

    Stack<Character> stack = new Stack<>();

    for (char c : chars) {
      if (isOpening(c)) {
        stack.push(c);
      } else {
        char cc = stack.pop();
        if (!pair(cc, c)) {
          System.out.printf("ERROR %c, but found %c instead%n", cc, c);
          return getPoints(c);
        }
      }
    }

    StringBuilder sb = new StringBuilder();

    long score = 0;
    while(stack.size() > 0) {
      char c = stack.pop();
      char closing = getClosingChar(c);
      sb.append(closing);

      score *= 5;
      score += getAutoPoints(closing);
    }

    System.out.printf("Completed by adding: %10s -> %2d %n", sb.toString(), score);

    return score;
  }

  private char getClosingChar(char c) {
    return switch (c) {
      case '(' -> ')';
      case '[' -> ']';
      case '{' -> '}';
      case '<' -> '>';
      default -> '-';
    };
  }

  private boolean pair(char o, char c) {
    if (o == '(' && c == ')') return true;
    if (o == '[' && c == ']') return true;
    if (o == '{' && c == '}') return true;
    if (o == '<' && c == '>') return true;
    return false;
  }

  private boolean isOpening(char c) {
    return switch (c) {
      case '(', '[', '{', '<' -> true;
      default -> false;
    };
  }

  private int getPoints(char c) {
    return switch (c) {
      case ')' -> 3;
      case ']' -> 57;
      case '}' -> 1197;
      case '>' -> 25137;
      default -> 0;
    };
  }

  private long getAutoPoints(char c) {
    return switch (c) {
      case ')' -> 1;
      case ']' -> 2;
      case '}' -> 3;
      case '>' -> 4;
      default -> 0;
    };
  }

}
