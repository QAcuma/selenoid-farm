package ru.acuma.selenoid.farm.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.acuma.selenoid.farm.model.dto.WebContainerDTO;
import ru.acuma.selenoid.farm.service.WebContainerService;

@RestController
@RequestMapping("/container")
@RequiredArgsConstructor
public class WebDriverController {

    private final WebContainerService webContainerService;

    @SneakyThrows
    @PostMapping("/create")
    public ResponseEntity<WebContainerDTO> createContainer(@RequestBody WebContainerDTO url) {
        WebContainerDTO container = webContainerService.getContainer(url.getUrl());
        return new ResponseEntity<>(container, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/stop")
    public ResponseEntity<Void> stopContainer(@RequestBody WebContainerDTO container) {
        webContainerService.stopContainer(container);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
