package ru.acuma.selenoid.farm.service.schedulled;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.acuma.selenoid.farm.model.domain.WebDriverContext;
import ru.acuma.selenoid.farm.service.WebDriverHolderService;

@Service
@RequiredArgsConstructor
public class WatchService {

    private final WebDriverHolderService webDriverHolderService;

    @Async
    @Scheduled(cron = "0/1 * * * * *")
    void watchActions() {
        var context = webDriverHolderService.getContext();
        context.forEach(item -> {
            logAction(() ->
                    parseTabs(item));
        });
    }

    private void parseTabs(WebDriverContext context) {
        var tabs = context.getWebDriver().getWindowHandles();
        tabs.forEach(tab -> {
            var currentTab = context.getWebDriver().switchTo().window(tab);
            var currentUrl = currentTab.getCurrentUrl();
            if (!context.getLastUrl().equals(currentUrl)) {
                context.getUserPath().add(currentUrl);
            }
            if (!normalizeUrl(currentUrl).contains(normalizeUrl(context.getBaseUrl()))) {
                if (tabs.size() > 1) {
                    currentTab.close();
                    return;
                }
                currentTab.get(context.getBaseUrl());
            }
        });
    }

    private void logAction(Runnable task) {
        task.run();
    }

    private String normalizeUrl(String url) {
        return url
                .replaceAll("http://", "https://")
                .replaceAll("www.", "");
    }


}
