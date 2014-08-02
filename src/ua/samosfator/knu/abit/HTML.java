package ua.samosfator.knu.abit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HTML {
    public static void createIndex(List<Faculty> faculties) throws FileNotFoundException, UnsupportedEncodingException {
        new File("www/pages").mkdir();
        PrintWriter pw = new PrintWriter("www/index.html", "UTF-8");
        String html = getHtmlHeader(-1) + "<body><div id=\"list\">";

        for (Faculty faculty : faculties) {
            html += "<p><a href=\"pages/" + faculty.getCode() + "/index.html\">" + faculty.getName() + "</a></p>";
        }

        html += "</div>" + getUpdateTimeHtml() + "</body>" + getHtmlAfterBody(-1);

        pw.println(html);
        pw.close();
    }

    private static String getUpdateTimeHtml() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");
        return "<p id=\"date\">Сторінку оновлено о " + ldt.format(dtf) + "</p>";
    }

    public static void createFacultyPages(Faculty faculty, List<Direction> directions) throws FileNotFoundException, UnsupportedEncodingException {
        String html = "";

        String folderName = "www/pages/" + faculty.getCode();
        new File(folderName).mkdir();
        PrintWriter pw = new PrintWriter(folderName + "/index.html", "UTF-8");

        html = getHtmlHeader(2) + "<body><div id=\"list\">";
        for (Direction direction : directions) {
            html += "<p><a href=\"" + direction.getCode() + "/index.html\">" + direction.getName() + "</a></p>";
        }
        html += "</div>" + getUpdateTimeHtml() + "</body>" + getHtmlAfterBody(2);

        pw.println(html);
        pw.close();
    }

    public static void writeEntrants(List<Entrant> list) throws FileNotFoundException, UnsupportedEncodingException {
        Direction dir = null;
        try {
            dir = list.get(0).getDirection();
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        String folderName = "www/pages/" + dir.getFaculty().getCode() + "/" + dir.getCode();
        String html = "";
        int counter = 0;

        new File(folderName).mkdir();
        PrintWriter pw = new PrintWriter(folderName + "/index.html", "UTF-8");

        Document document = Jsoup.parse(getHtmlHeader(3) + "<body><table><thead><tr><th>#</th><th>ПІБ</th><th>Бал</th><th>ПК</th>" +
                "<th>ПЧ</th><th>Оригінали</th></tr></thead><tbody></tbody></table></body>" + getHtmlAfterBody(3));

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

    private static String getHtmlHeader(int nestingLevel) {
        return "<html><head><meta charset=\"utf-8\">" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + createDots(nestingLevel) + "css/style.css\">" +
                "<script src=\"" + createDots(nestingLevel) + "js/jquery.js\">" +
                "</script><script src=\"" + createDots(nestingLevel) + "js/tablesorter.js\"></script></head>";
    }

    private static String getHtmlAfterBody(int nestingLevel) {
        return "<script src=\"" + createDots(nestingLevel) + "js/script.js\"></script>" +
                "<script>(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){ (i[r].q=i[r].q" +
                "||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o), m=s.getElementsByTagName(o)[0];a.a" +
                "sync=1;a.src=g;m.parentNode.insertBefore(a,m) })(window,document,'script','//www.google-analytics.c" +
                "om/analytics.js','ga'); ga('create', 'UA-46344654-6', 'auto'); ga('send', 'pageview');</script>" +
                "</html>";
    }

    private static String createDots(int level) {
        String dots = "";
        for (int i = 0; i < level; i++) {
            dots += "../";
        }
        return dots;
    }
}
