package Client;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling {
    String weatherInfo = null;

    public String getWeatherInfo() {
        return weatherInfo;
    }

    public Crawling() {
        String WeatherURL = "https://weather.naver.com/today";
        Document doc = null;

        try {
            doc = org.jsoup.Jsoup.connect(WeatherURL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        // HTML로 부터 데이터 가져오기
        Element element_title = doc.selectFirst("#wrap .location_area");

        // 1. 지역 데이터
        String title = element_title.select("strong").text();
        sb.append(title).append("\n");

        // ------------------------------------------------------------------------------------------------------- //

        // HTML로 부터 데이터 가져오기
        Element element_degree = doc.selectFirst("#content .today_weather .weather_area .weather_now .summary_img");
        String degree = element_degree.selectFirst(".current").ownText();
        sb.append("현재 온도 : " + degree + "°").append("\n");

        // ------------------------------------------------------------------------------------------------------- //

        // HTML로 부터 데이터 가져오기
        Element element_weather = doc.selectFirst("#content .today_weather .weather_area .summary");
        // <p class="summary"><span class="weather">맑음</span> <em>어제보다</em> <span class="temperature down">3.9° <span class="blind">낮아요</span></span></p>

        // 2. 날씨 데이터
        String weather = element_weather.selectFirst("p").text();
        sb.append(weather);

        weatherInfo = sb.toString();
    }
}