package ar.edu.itba.paw.webapp.form.annotations;

import javax.validation.GroupSequence;

@GroupSequence({ DateAndTimeCreate.class, MultitripSearch.class, Price.class })
public @interface SearchTripGroupSequence {
}
