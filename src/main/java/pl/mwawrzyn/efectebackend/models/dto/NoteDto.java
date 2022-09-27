package pl.mwawrzyn.efectebackend.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoteDto {

    private Long id;

    private Integer version;

    private Date modificationDate;

    private String content;
}
