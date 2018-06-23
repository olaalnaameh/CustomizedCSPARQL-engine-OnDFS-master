/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.cep.api;

import java.util.Observable;

public class JSONStream extends Observable {

	private long lastUpdated = 0;

	private String iri = "";

	public String getIRI() {
		return this.iri;
	}

	public String uniqueName() {
		long hashCode = this.iri.hashCode();
		hashCode = hashCode + Integer.MAX_VALUE + 1000;
		return "STREAM" + String.valueOf(hashCode);
	}

	public JSONStream(final String iri) {
		this.iri = iri;
	}

	public void put(final JSONData j) {

		lastUpdated = System.nanoTime();
		setChanged();
		this.notifyObservers(j);
	}

	private long getLastUpdated() {
		return lastUpdated;
	}

}

