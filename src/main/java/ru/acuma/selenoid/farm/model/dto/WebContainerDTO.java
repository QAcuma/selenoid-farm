package ru.acuma.selenoid.farm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class WebContainerDTO {

    @JsonProperty
    String url;

    @JsonProperty
    String sessionId;

}
