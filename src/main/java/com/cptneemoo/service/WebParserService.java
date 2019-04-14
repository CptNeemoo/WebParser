package com.cptneemoo.service;

import com.cptneemoo.exception.WebParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class WebParserService {
    public HashMap<String, String> parseUrl(String url) throws WebParseException {
        HashMap<String, String> information = new HashMap<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            String name = doc.getElementsByAttribute("data-qaid").select("h1").html();
            information.put("name", name);
            String availability = doc.getElementsByClass("x-product-presence").html();
            information.put("availability", availability);
            Elements itemProps = doc.getElementsByAttribute("itemprop");
            String price;
            for (Element element : itemProps) {
                if (element.attr("itemprop").equals("price")) {
                    price = element.text();
                    information.put("price", price);
                }
            }
            String color = doc.getElementsByAttributeValue("data-qaid-value", "30810").text();
            information.put("color", color);
            String description = doc.getElementsByAttributeValue("data-qaid", "product_description").text();
            information.put("description", description);
            String imageUrl = doc.getElementsByAttributeValue("itemprop", "image").select("link").attr("href");
            information.put("imageUrl", imageUrl);
        } catch (IOException e) {
            throw new WebParseException("Can't parse the web page");
        }
        return information;
    }
}
