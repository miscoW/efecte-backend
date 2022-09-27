package pl.mwawrzyn.efectebackend.models.dto;

import lombok.Data;

@Data
public class ErrorDto {
    int errorCode;
    String massage;
}
