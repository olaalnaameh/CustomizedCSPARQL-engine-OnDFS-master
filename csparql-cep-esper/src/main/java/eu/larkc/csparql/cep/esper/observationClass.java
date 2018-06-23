package eu.larkc.csparql.cep.esper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class observationClass {

	private final String procedure;
	private final String property;
	private final String type;
	private final String mURI;

	observationClass(final String procedure, final String property,
			final String type, final String mURI) {
		this.procedure = procedure;
		this.property = property;
		this.type = type;
		this.mURI = mURI;
	}

	public String getProcedure() {
		return procedure;
	}

	public String getProperty() {
		return property;
	}

	public String getType() {
		return type;
	}

	public String getmURI() {
		return mURI;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(procedure).append(property)
				.append(type).append(mURI).toHashCode();// two randomly
		// chosen prime
		// numbers
		// if deriving: appendSuper(super.hashCode()).

	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof observationClass)) { return false; }
		if (obj == this) { return true; }

		final observationClass o = (observationClass) obj;
		return new EqualsBuilder().append(procedure, o.procedure)
				.append(property, o.property).append(type, o.type)
				.append(mURI, o.mURI).isEquals();// if deriving:
													// appendSuper(super.equals(obj)).
	}
}
