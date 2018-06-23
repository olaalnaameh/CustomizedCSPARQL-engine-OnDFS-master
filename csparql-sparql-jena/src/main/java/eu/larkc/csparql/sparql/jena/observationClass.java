package eu.larkc.csparql.sparql.jena;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.hp.hpl.jena.rdf.model.Resource;

public class observationClass {
	private final String sensor;
	private final String property;
	private final String phenomenon;
	private final String mURI;

	observationClass(final String sensor, final String property,
			final String phenomenon, final String mURI) {
		this.sensor = sensor;
		this.property = property;
		this.phenomenon = phenomenon;
		this.mURI = mURI;
	}

	public String getSensor() {
		return sensor;
	}

	public String getProperty() {
		return property;
	}

	public String getPhenomenon() {
		return phenomenon;
	}

	public String getmURI() {
		return mURI;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(sensor).append(property)
				.append(phenomenon).append(mURI).toHashCode();// two randomly
		// chosen prime
		// numbers
		// if deriving: appendSuper(super.hashCode()).

	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof observationClass))
			return false;
		if (obj == this)
			return true;

		final observationClass o = (observationClass) obj;
		return new EqualsBuilder().append(sensor, o.sensor)
				.append(property, o.property).append(phenomenon, o.phenomenon)
				.append(mURI, o.mURI).isEquals();// if deriving:
													// appendSuper(super.equals(obj)).
	}

}
