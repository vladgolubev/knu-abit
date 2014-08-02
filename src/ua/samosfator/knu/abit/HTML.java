package ua.samosfator.knu.abit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class HTML {
    private static final String HEAD = "<html><head><meta charset=\"utf-8\">" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">" +
            "<script src=\"js/jquery.js\"></script><script src=\"js/tablesorter.js\"></script></head>";
    public static final String AFTER_BODY = "<script src=\"js/script.js\"></script></html>";

    public static void writeIndex(List<Faculty> faculties) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter pw = new PrintWriter("www/index.html", "UTF-8");
        String html = HEAD + "<body><div id=\"faculties\">";

        for (Faculty faculty : faculties) {
            html += "<p><a href=\"" + faculty.getCode() + ".html\">" + faculty.getName() + "</a></p>";
        }

        html += "</div></body>" + AFTER_BODY;

        pw.println(html);
        pw.close();
    }

    public static void writeList(List<Entrant> list) throws FileNotFoundException, UnsupportedEncodingException {
        Direction dir = list.get(0).getDirection();
        int counter = 0;

        //Example filename - 933-147451-1.html
        PrintWriter pw = new PrintWriter("www/" + dir.getFaculty().getCode() +
                "-" + dir.getCode() + "-" + dir.getWave() + ".html", "UTF-8");

        Document document = Jsoup.parse(HEAD + "<body><table><thead><tr><th>#</th><th>ПІБ</th><th>Бал</th><th>ПК</th>" +
                "<th>ПЧ</th><th>Оригінали</th></tr></thead><tbody></tbody></table></body>" + AFTER_BODY);

        Element table = document.select("table tbody").first();

        for (Entrant entrant : list) {
            counter++;
            table.append("<tr>" +
                    "<td>" + counter + "</td>" +
                    "<td>" + entrant.getName() + "</td>" +
                    "<td>" + entrant.getScore() + "</td>" +
                    "<td>" + entrant.getPK() + "</td>" +
                    "<td>" + entrant.getPCH() + "</td>" +
                    "<td>" + entrant.getOriginals() + "</td>" +
                    "</tr>");
        }

        pw.println(document);
        pw.close();
    }
}
