package ua.samosfator.knu.abit;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Faculty> faculties = Faculty.getFaculties(2014);

        HTML.writeIndex(faculties);

//        for (Faculty faculty : faculties) {
//            for (Direction direction : Direction.getDirections(faculty)) {
//                Entrant.getEntrants(direction, 1);
//            }
//        }
    }
}
