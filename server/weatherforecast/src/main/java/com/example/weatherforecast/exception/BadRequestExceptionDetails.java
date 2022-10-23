package com.example.weatherforecast.exception;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails {}
