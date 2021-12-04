package io.hardtke.adventofcode.days;

import java.util.*;

public class Day_04 extends Day {

  private static class Bingo {
    private Map<Integer, Map<Integer, Integer>> grid = new HashMap<>();

    public Bingo(List<String> lines) {
      for (int i = 0; i < lines.size(); i++) {
        Map<Integer, Integer> row = new HashMap<>();
        String[] numbers = lines.get(i).trim().split("\\s+");
        for (int j = 0; j < lines.size(); j++) {
          row.put(j, Integer.parseInt(numbers[j]));
        }
        this.grid.put(i, row);
      }
    }

    public boolean checkIfWon(List<Integer> numbers) {
      for (int i = 0; i < this.grid.size(); i++) {
        Collection<Integer> row = this.grid.get(i).values();
        if (numbers.containsAll(row) && numbers.size() >= row.size()) return true;
      }
      for (int i = 0; i < this.grid.size(); i++) {
        Collection<Integer> col = new LinkedList<>();
        for (int j = 0; j < this.grid.size(); j++) {
          col.add(this.grid.get(j).get(i));
        }
        if (numbers.containsAll(col) && numbers.size() >= col.size()) return true;
      }
      return false;
    }

    public Collection<Integer> getAllNumbersBut(Collection<Integer> numbers) {
      Set<Integer> allNumbers = new HashSet<>();
      this.grid.forEach((i, row) -> {
        allNumbers.addAll(row.values());
      });
      allNumbers.removeAll(numbers);
      return allNumbers;
    }

    public void print() {
      for (int i = 0; i < this.grid.size(); i++) {
        for (int j = 0; j < this.grid.size(); j++) {
          System.out.printf("%2d ", this.grid.get(i).get(j));
        }
        System.out.println();
      }
    }
  }

  @Override
  public void calculate() {

    LinkedList<Integer> drawnNumbers = new LinkedList<>();

    String rawNumbers = this.getLines().get(0);
    this.getLines().remove(0);

    for (String rawNumber : rawNumbers.split(",")) {
      drawnNumbers.push(Integer.valueOf(rawNumber));
    }
    Collections.reverse(drawnNumbers);

    List<List<String>> rawBoards = new ArrayList<>();

    List<String> currentBoard = null;

    for (String line : this.getLines()) {
      if (currentBoard == null) currentBoard = new LinkedList<>();
      if (line.trim().length() == 0) {
        if (currentBoard.size() == 0) continue;
        rawBoards.add(currentBoard);
        currentBoard = null;
        continue;
      }
      currentBoard.add(line);
    }
    rawBoards.add(currentBoard);

    List<Bingo> boards = new ArrayList<>();

    for (List<String> rawBoard : rawBoards) {
      Bingo b = new Bingo(rawBoard);
      boards.add(b);
      System.out.println("adding");
    }

    List<Integer> lasWinningNumbers = null;
    Bingo lastWinner = null;

    x : for (int i = 0; i < drawnNumbers.size(); i++) {
      List<Integer> currentNumbers = new LinkedList<>(drawnNumbers.subList(0, i));
      Bingo remove = null;
      for (Bingo board : boards) {
        if (board.checkIfWon(currentNumbers)) {
          //System.out.println(boards.size());
          System.out.println(currentNumbers);
          board.print();
          int sum = board.getAllNumbersBut(currentNumbers).stream().mapToInt(Integer::valueOf).sum();
          System.out.println(sum);
          System.out.println(sum*currentNumbers.get(currentNumbers.size() - 1));
          if (boards.size() == 1) {
            break x;
          } else {
            lasWinningNumbers = currentNumbers;
            lastWinner = board;
            remove = board;
          }
        }
      }
      if (remove != null) boards.remove(remove);
    }

    if (lastWinner != null) {
      System.out.println("LastWinner:");
      System.out.println(lasWinningNumbers);
      lastWinner.print();
      int sum = lastWinner.getAllNumbersBut(lasWinningNumbers).stream().mapToInt(Integer::valueOf).sum();
      System.out.println(sum);
      System.out.println(sum*lasWinningNumbers.get(lasWinningNumbers.size() - 1));
    }

  }

  private void calc2(int pos, StringBuilder most, StringBuilder least) {

  }

}
