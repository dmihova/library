package com.tinqin.academy.library.core.processors.author;

import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthor;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorInput;
import com.tinqin.academy.library.api.operations.createauthor.CreateAuthorOutput;
import com.tinqin.academy.library.api.operations.createbook.CreateBookOutput;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.models.Book;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {

    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, CreateAuthorOutput> process(CreateAuthorInput input) {
        return Try.of(() -> conversionService.convert(input, Author.class))
                .flatMap(this::saveAuthor)
                .toEither()
                .mapLeft(errorHandler::handle);
      }

    private Try<CreateAuthorOutput> saveAuthor(Author author) {
        return Try.of(() -> authorRepository.save(author))
                .map(savedBook -> CreateAuthorOutput.builder()
                        .id(savedBook.getId())
                        .build());
    }
}
