package com.tinqin.academy.library.api.operations.getbook;

import com.tinqin.academy.library.api.base.ProcessorOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetBookOutput implements ProcessorOutput {
    private String author;
    private String title;
    private String pages;
}
