package com.PohonTautan.Entity;

import java.sql.Blob;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "style")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Styles extends DateAudity{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_style")
    private Integer id_style;

    // @Column(name = "nama_image")
    // private String image;

    // @Column(name = "nama_bg")
    // private String bg;
    
    @Lob
    @Column(name = "image")
    private Blob image;

    @Lob
    @Column(name = "bg")
    private Blob bg;

    @Column(name = "button_style")
    private String button_style;

    @Column(name = "button_name")
    private String button_name;

    @Column(name = "button_animation")
    private String button_animation;

    @Column(name = "button_text_color")
    private String button_text_color;

    @Column(name = "link")
    private String link;

    @Column(name = "id_user")
    private Integer id_user;

    @Column(name = "custom_url")
    private String custom_url;

    @Column(name = "headline")
    private String headline;

    @Column(name = "bio")
    private String bio;

    @Column(name = "bg_default")
    private String bg_default;
}
