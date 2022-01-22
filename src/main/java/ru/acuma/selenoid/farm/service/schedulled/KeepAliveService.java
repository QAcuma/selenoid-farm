package ru.acuma.selenoid.farm.service.schedulled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.acuma.selenoid.farm.service.WebDriverHolderService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeepAliveService {

    private final WebDriverHolderService webDriverHolderService;

    @Scheduled(cron = "0/15 * * * * *")
    private void keepAlive() {
        var drivers = webDriverHolderService.getDrivers();
        try {
        drivers.forEach(WebDriver::getCurrentUrl);
        } catch (NoSuchWindowException e) {
            log.warn("Window has been closed");
        }
    }

}
