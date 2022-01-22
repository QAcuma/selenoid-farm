package ru.acuma.selenoid.farm.service.impl;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;
import ru.acuma.selenoid.farm.model.domain.WebDriverContext;
import ru.acuma.selenoid.farm.service.WebDriverHolderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebDriverContextServiceImpl implements WebDriverHolderService {

    private static final List<WebDriverContext> contextHolder = new ArrayList<>();

    @Override
    public List<WebDriver> getDrivers() {
        return contextHolder.stream().map(WebDriverContext::getWebDriver).collect(Collectors.toList());
    }

    @Override
    public List<WebDriverContext> getContext() {
        return contextHolder;
    }

    @Override
    public WebDriverContext getBySessionId(String sessionId) {
        return contextHolder.stream()
                .filter(item -> item.getSessionId().equals(sessionId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void removeBySessionId(String sessionId) {
        var context = getBySessionId(sessionId);
        contextHolder.remove(context);
    }

    @Override
    public WebDriverContext newDriver(RemoteWebDriver webDriver, String baseUrl) {
        WebDriverContext driver = new WebDriverContext(webDriver, baseUrl);
        contextHolder.add(driver);
        return driver;
    }

}
