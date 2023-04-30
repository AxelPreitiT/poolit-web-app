package ar.edu.itba.paw.webapp.form.annotations;

import javax.validation.GroupSequence;

@GroupSequence({ DateAndTime.class, Multitrip.class, Price.class })
public @interface SearchTripGroupSequence {
}
