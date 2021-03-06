package ua.samosfator.knu.abit;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Faculty> faculties = Faculty.getFaculties(2014);
        List<Direction> directions;
        List<Entrant> entrants;

        HTMLwriter.createIndex(faculties);

        for (Faculty faculty : faculties) {
            directions = Direction.getDirections(faculty);
            HTMLwriter.createFacultyPages(faculty, directions);

            for (Direction direction : directions) {
                entrants = Entrant.getEntrants(direction, 1);
                HTMLwriter.writeEntrants(entrants);
            }
        }
    }
}
