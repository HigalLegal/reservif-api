package com.reservif.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ImageUser {

    @Column(name = "default_image_url")
    private String defaultImageUrl;

    @Column(name = "thumb_image_url")
    private String thumbImageUrl;

}
