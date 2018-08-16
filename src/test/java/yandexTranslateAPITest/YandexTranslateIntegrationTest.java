package yandexTranslateAPITest;

import com.codeborne.selenide.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class YandexTranslateIntegrationTest {

    private static String query = "simple%20example";
    private static final String API_PATH = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20180816T113726Z.642dff2b171c250b.193fa69be8e66ff56779505808398814c0984450";
    private static String lang = "ru";
    private static String translateResult;

    @BeforeEach
    public void beforeTest() throws UnirestException {

        Configuration.browser = "chrome";

        String json = Unirest.get(API_PATH + "?key=" + API_KEY + "&text=" + query + "&lang=" + lang)
                .asString()
                .getBody();

        System.out.println("JSON response: " + json);

        translateResult = JsonPath.read(json, "text[0]");
        System.out.println("Get text from response: " + translateResult);
    }

    @Test
    public void restTest(){

        open("https://translate.yandex.ru/?lang=en-ru&text=simple%20example");
        $("[class='translation-chunk']").should(text(translateResult));

    }

}
