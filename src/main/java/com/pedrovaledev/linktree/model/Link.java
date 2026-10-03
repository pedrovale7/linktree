package com.pedrovaledev.linktree.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "links")
@NoArgsConstructor
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String url;

    @Column(nullable=false)
    private Integer clickCount = 0;

    public void incrementClickCount() {
        this.clickCount++;
    }

    public Link (String title, String url) {
        this.title = title;
        this.url = url;
    }

}
