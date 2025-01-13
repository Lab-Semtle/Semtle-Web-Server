package com.archisemtle.semtlewebserverspring.domain.activity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name="activity_image")
@NoArgsConstructor
public class ActivityImage {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long imageId;

    @Column(nullable = false, unique = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Activity activity;

    @Builder
    public ActivityImage(String imageUrl, Activity activity) {
        this.imageUrl = imageUrl;
        this.activity = activity;
    }



}
