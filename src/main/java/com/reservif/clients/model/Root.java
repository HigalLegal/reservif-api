package com.reservif.clients.model;

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
public class Root {

    private MyData data;

    private boolean success;

    private int status;

    // ----------------------------------------------------------

    public String defaultUrlImage() {
        return data.getImage().getUrl();
    }

    public String thumbUrlImage() {
        return data.getThumb().getUrl();
    }

}
