package ru.acuma.selenoid.farm.service;

import ru.acuma.selenoid.farm.model.dto.WebContainerDTO;

public interface WebContainerService {

    WebContainerDTO getContainer(String Url);

    String stopContainer(WebContainerDTO container);

}
