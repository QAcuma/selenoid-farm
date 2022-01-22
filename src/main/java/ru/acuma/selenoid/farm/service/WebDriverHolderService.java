package ru.acuma.selenoid.farm.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.acuma.selenoid.farm.model.domain.WebDriverContext;

import java.util.List;

public interface WebDriverHolderService {

    List<WebDriver> getDrivers();

    List<WebDriverContext> getContext();

    WebDriverContext getBySessionId(String sessionId);

    void removeBySessionId(String sessionId);

    WebDriverContext newDriver(RemoteWebDriver webDriver, String baseUrl);

}
