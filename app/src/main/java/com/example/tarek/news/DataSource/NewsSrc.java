package com.example.tarek.news.DataSource;

public class NewsSrc {

    private String sectioName;
    private String webtitle;
    private String webUrl;
    private String date;
    private String bodyText;
    private String imageUrl;

    public NewsSrc(String sectioName, String webtitle, String webUrl, String date, String bodyText, String imageUrl) {
        this.sectioName = sectioName;
        this.webtitle = webtitle;
        this.webUrl = webUrl;
        this.date = date;
        this.bodyText = bodyText;
        this.imageUrl = imageUrl;
    }

    public String getSectioName() {
        return sectioName;
    }

    public String getWebtitle() {
        return webtitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getDate() {
        return date;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
