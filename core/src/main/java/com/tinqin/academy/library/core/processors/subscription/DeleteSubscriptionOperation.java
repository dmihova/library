package com.tinqin.academy.library.core.processors.subscription;


import com.tinqin.academy.library.api.errors.OperationError;
import com.tinqin.academy.library.api.operations.subscription.deletesubscription.DeleteSubscription;
import com.tinqin.academy.library.api.operations.subscription.deletesubscription.DeleteSubscriptionInput;
import com.tinqin.academy.library.api.operations.subscription.deletesubscription.DeleteSubscriptionResult;
import com.tinqin.academy.library.core.errorhandler.base.ErrorHandler;
import com.tinqin.academy.library.core.errorhandler.exceptions.BusinessException;
import com.tinqin.academy.library.persistence.models.Subscription;
import com.tinqin.academy.library.persistence.repositories.SubscriptionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.academy.library.api.ValidationMessages.SUBSCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DeleteSubscriptionOperation implements DeleteSubscription {
    private final SubscriptionRepository subscriptionRepository;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, DeleteSubscriptionResult> process(DeleteSubscriptionInput input) {
        return fetchSubscription(input)
                .flatMap(this::deleteSubscription)
                .toEither()
                .mapLeft(errorHandler::handle);
    }

    private Try<Subscription> fetchSubscription(DeleteSubscriptionInput input) {
        return Try.of(() -> UUID.fromString(input.getSubscriptionId()))
                .flatMap(id -> Try.of(() -> subscriptionRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(SUBSCRIPTION_NOT_FOUND))));
    }


    private Try<DeleteSubscriptionResult> deleteSubscription(Subscription subscription) {
        subscription.setCanRent(false);
        return Try.of(() -> subscriptionRepository.save(subscription))
                .map(savedSubscription -> DeleteSubscriptionResult.builder()
                        .id(savedSubscription.getId().toString())
                        .build());
    }

}
