package com.maace.connectEtec.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {
    String message() default "Lista não pode estar vazia";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


