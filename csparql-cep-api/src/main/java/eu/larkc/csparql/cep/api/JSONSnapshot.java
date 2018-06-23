/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.cep.api;

import java.util.List;
import java.util.Observable;

import eu.larkc.csparql.common.NamedObject;

public class JSONSnapshot extends Observable implements
NamedObject {

	private String id = "";

	public String getId() {
		return this.id;
	}

	public JSONSnapshot(final String id) {
		this.id = id;
	}

	public void put(final List<JSONData> j) {

		setChanged();
		this.notifyObservers(j);
	}

}
