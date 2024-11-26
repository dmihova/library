package com.tinqin.academy.library.core.processors.author;


import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.models.author.AuthorModel;
import com.tinqin.academy.library.api.models.book.BookModel;
import com.tinqin.academy.library.api.operations.createbook.CreateBookOutput;
import com.tinqin.academy.library.api.operations.getauthor.GetAuthor;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthor;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorInput;
import com.tinqin.academy.library.api.operations.queryauthor.QueryAuthorOutput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookInput;
import com.tinqin.academy.library.api.operations.querybook.QueryBookOutput;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.persistence.models.Author;
import com.tinqin.academy.library.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAuthorOperation implements QueryAuthor {
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    //criteria builder - separate component to build complicated cases
    @Override
    public Either<OperationError, QueryAuthorOutput> process(QueryAuthorInput input) {

        return findAuthors(input)
                .map(authorModelList -> QueryAuthorOutput
                        .builder()
                        .authorModelList(authorModelList)
                        .build()
                )
                .toEither()
                .mapLeft(errorHandler::handle);

    }

    private Try<List<AuthorModel>> findAuthors( QueryAuthorInput input)  {
        return Try.of(() ->  getAuthorsByParameter(input)
                .stream()
                .map(authorEntity -> conversionService.convert(authorEntity, AuthorModel.class))
                .toList());

    }

    private Collection<Author> getAuthorsByParameter(QueryAuthorInput input) {
        if (!input.getLastName().isBlank()&&!input.getFirstName().isBlank()) {
            return authorRepository.findAllByLastNameAndFirstName(input.getLastName(),input.getFirstName());
        } else if (!input.getLastName().isBlank()) {
            return authorRepository.findAllByLastName(input.getLastName());
        } else if (!input.getFirstName().isBlank()) {
            return authorRepository.findAllByFirstName(input.getFirstName());
        }
        else {
            return authorRepository.findAll();
        }

    }
}
