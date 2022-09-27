package pl.mwawrzyn.efectebackend.models.dto;

import lombok.Data;

@Data
public class NoteDto {

    private Long id;

    private Integer version;

    private String content;
}
