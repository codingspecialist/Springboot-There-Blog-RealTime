package shop.mtcoding.thereblog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JsoupTest {
    String html = "<div id=\"weather\">10도</div>\n" +
            "<div class=\"loc\"><h1>서울</h1></div>";

    @Test
    public void jsoup_test(){
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("#weather");
        System.out.println(elements.get(0).text());

        Elements elements2 = doc.select(".loc");
        System.out.println(elements2.get(0).text());
    }

    @Test
    public void watch_test() throws IOException {
        //
        Document doc = Jsoup.connect("https://www.cartier.com/ko-kr/watches/all-collections/tank/").get();
        System.out.println(doc.toString());
    }
}
