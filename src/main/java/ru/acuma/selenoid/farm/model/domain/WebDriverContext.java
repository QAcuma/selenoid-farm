package ru.acuma.selenoid.farm.model.domain;

import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class WebDriverContext {

    public WebDriverContext(RemoteWebDriver webDriver, String baseUrl) {
        this.webDriver = webDriver;
        this.baseUrl = baseUrl;
        this.sessionId = webDriver.getSessionId().toString();
        this.userPath.add(baseUrl);
    }

    String sessionId;

    RemoteWebDriver webDriver;

    String baseUrl;

    List<String> userPath = new ArrayList<>();

    public String getLastUrl() {
        return Iterables.getLast(userPath);
    }

}
