package eu.larkc.csparql.sparql.jena;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.hp.hpl.jena.rdf.model.Resource;

public class measurementClass {
	private final Float value;
	private final String uom;

	measurementClass(final Float value, final String uom) {
		this.value = value;
		this.uom = uom;
	}

	public Float getValue() {
		return this.value;
	}

	public String getUOM() {
		return this.uom;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(value).append(uom)
				.toHashCode();// two randomly chosen prime numbers
		// if deriving: appendSuper(super.hashCode()).

	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof measurementClass))
			return false;
		if (obj == this)
			return true;

		final measurementClass m = (measurementClass) obj;
		return new EqualsBuilder().append(value, m.value).append(uom, m.uom)
				.isEquals();// if deriving: appendSuper(super.equals(obj)).
	}

}
