package ru.acuma.selenoid.farm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.acuma.selenoid.farm.model.dto.WebContainerDTO;
import ru.acuma.selenoid.farm.service.WebContainerService;
import ru.acuma.selenoid.farm.service.WebDriverHolderService;

import java.net.URL;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebContainerServiceImpl implements WebContainerService {

    private final WebDriverHolderService webDriverHolderService;

    @Value("${selenoid.url}")
    String selenoidUrl;

    @SneakyThrows
    @Override
    public WebContainerDTO getContainer(String url) {
        Capabilities capabilities = getChromeCapabilities();
        RemoteWebDriver driver = initDriver(capabilities);
        webDriverHolderService.newDriver(driver, url);
        openUrl(driver, url);
        return new WebContainerDTO(url, driver.getSessionId().toString());
    }

    @Override
    public String stopContainer(WebContainerDTO container) {
        var context = webDriverHolderService.getBySessionId(container.getSessionId());
        webDriverHolderService.removeBySessionId(container.getSessionId());
        context.getWebDriver().close();
        context.getWebDriver().quit();
        return container.getSessionId();
    }

    private void openUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    private Capabilities getChromeCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions
                .addArguments("--start-maximized")
                .addArguments("--start-fullscreen")
                .addArguments("--kiosk")
                .addArguments("--disable-experimental-fullscreen-exit-ui")
                .addArguments("--enable-strict-powerful-feature-restrictions")
                .addArguments("--disable-extensions")
                .addArguments("--single-tab-mode")
                .addArguments("--silent")
                .addArguments("--incognito")
                .addArguments("--sandbox")
                .addArguments("--disable-default-apps")
                .addArguments("--apps-keep-chrome-alive-in-tests")
                .addArguments("--hide_web_store_icon")
                .addArguments("--disable-popup-blocking")
                .addArguments("--ignore-certificate-errors")
                .setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "browserName", "chrome",
                "browserVersion", "96.0",
                "enableVNC", true,
                "enableVideo", true
        ));
        capabilities.setCapability("chromeOptions", chromeOptions);
        return capabilities;
    }


    @SneakyThrows
    public RemoteWebDriver initDriver(Capabilities capabilities) {
        return new RemoteWebDriver(new URL(selenoidUrl), capabilities);
    }

}
