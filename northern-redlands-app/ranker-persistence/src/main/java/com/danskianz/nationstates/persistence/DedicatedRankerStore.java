package com.danskianz.nationstates.persistence;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface DedicatedRankerStore {
    
}
