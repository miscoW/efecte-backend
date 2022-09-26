package pl.mwawrzyn.efectebackend.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private String content;
}
