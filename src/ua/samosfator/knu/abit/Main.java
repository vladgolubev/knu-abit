package ua.samosfator.knu.abit;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for (Faculty faculty : Faculty.getFaculties(2014)) {
            for (Direction direction : Direction.getDirections(faculty)) {
                Entrant.getEntrants(direction, 1);
            }
        }
    }
}
