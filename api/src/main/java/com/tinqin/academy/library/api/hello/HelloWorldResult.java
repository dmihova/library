package com.tinqin.academy.library.api.hello;


import com.tinqin.academy.library.api.base.ProcessorOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HelloWorldResult implements ProcessorOutput {
    private final String message;
}
