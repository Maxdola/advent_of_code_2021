package io.hardtke.adventofcode.days;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day_01 extends Day {

    @Override
    public void calculate() {

        List<Integer> ints = this.getLines().stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        int count = 0;

        for (int i = 1; i < ints.size(); i++) {
            int previous = ints.get(i - 1);
            if (previous < ints.get(i)) {
                count++;
            }
        }

        System.out.println("Greater than: " + count);

        List<Integer> triplets = new LinkedList<>();

        int start = 0;

        while (start + 2 < this.getLines().size()) {
            triplets.add(ints.get(start) + ints.get(start + 1) + ints.get(start + 2));
            start++;
        }

        int countB = 0;

        for (int i = 1; i < triplets.size(); i++) {
            int previous = triplets.get(i - 1);
            if (previous < triplets.get(i)) {
                countB++;
            }
        }

        System.out.println("Greater than B: " + countB);

    }

}
