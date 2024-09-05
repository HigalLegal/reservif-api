package com.reservif.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyData {

    private String id;

    private String title;

    @JsonProperty("url_viewer")
    private String urlViewer;

    private String url;

    @JsonProperty("display_url")
    private String displayUrl;

    private int width;

    private int height;

    private int size;

    private int time;

    private int expiration;

    private Image image;

    private Thumb thumb;

    private Medium medium;

    @JsonProperty("delete_url")
    private String deleteUrl;


}
