package net.jadler.stubbing.server.aaa;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;


public class Pokus {
    //validateThat(aaa).named("aaa").is(greaterThan(1));
    
    public static <T> Named<T> validateThat(T validatee) {
        return new OngoingValidation<T>(validatee);
    }
    
    
    public static class OngoingValidation<T> implements Named<T>, Is<T> {
        private final T validatee;
        private String name;
        
        public OngoingValidation(final T validatee) {
            this.validatee = validatee;
        }
        
        
        @Override
        public Is<T> named(final String name) {
            //validateThat(name).named("name").is(notNullValue()); infinite loop
            this.name = name;
            return thdis;
        }
        
        
        @Override
        public void is(final Matcher<? super T> predicate) {
            if (!predicate.matches(this.validatee)) {
                final Description desc = new StringDescription();
                predicate.describeMismatch(this.validatee, desc);
                throw new IllegalArgumentException(desc.toString());
            }
        }
    }
    
    
    public interface Named<T> {
        Is<T> named(String name);
    }
    
    
    public interface Is<T> {
        void is(Matcher<? super T> predicate);
    }
    
}
