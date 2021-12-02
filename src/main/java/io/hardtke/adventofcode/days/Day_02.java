package io.hardtke.adventofcode.days;


import java.util.concurrent.atomic.AtomicInteger;

public class Day_02 extends Day {

    @Override
    public void calculate() {

        AtomicInteger f = new AtomicInteger();
        AtomicInteger d = new AtomicInteger();

        this.getLines().forEach(l -> {
            String[] parts = l.split( "\\s+");
            if (parts[0].equalsIgnoreCase("forward")) {
                f.addAndGet(Integer.parseInt(parts[1]));
            } else if (parts[0].equalsIgnoreCase("down")) {
                d.addAndGet(Integer.parseInt(parts[1]));
            } else if (parts[0].equalsIgnoreCase("up")) {
                d.addAndGet(-Integer.parseInt(parts[1]));
            }
        });

        System.out.printf("Tiefe %d, Forward: %d => %d%n", d.get(), f.get(), f.get() * d.get());

        calc2();

    }

    private void calc2() {
        AtomicInteger f = new AtomicInteger();
        AtomicInteger d = new AtomicInteger();
        AtomicInteger aim = new AtomicInteger();

        this.getLines().forEach(l -> {
            String[] parts = l.split( "\\s+");
            if (parts[0].equalsIgnoreCase("forward")) {
                f.addAndGet(Integer.parseInt(parts[1]));
                d.addAndGet(Integer.parseInt(parts[1]) * aim.get());
            } else if (parts[0].equalsIgnoreCase("down")) {
                aim.addAndGet(Integer.parseInt(parts[1]));
            } else if (parts[0].equalsIgnoreCase("up")) {
                aim.addAndGet(-Integer.parseInt(parts[1]));
            }
        });

        System.out.printf("Tiefe %d, Forward: %d => %d%n", d.get(), f.get(), f.get() * d.get());
    }

}
