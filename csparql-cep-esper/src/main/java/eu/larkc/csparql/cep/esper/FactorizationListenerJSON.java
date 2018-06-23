package eu.larkc.csparql.cep.esper;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.HashMap;
import java.math.BigDecimal;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfSnapshot;
import eu.larkc.csparql.cep.api.JSONData;
import eu.larkc.csparql.cep.api.RdfStream;


class FactorizationListenerJSON extends RdfSnapshot implements UpdateListener {
	public static HashMap<measurementClass, String> mapMeasurement ;
	public static HashMap<observationClass, String> mapObservation ;
	private List<RdfQuadruple> quads;
	private static RdfQuadruple q;
	private static long systemTime;
	
   FactorizationListenerJSON(final String id) {
      super(id);
   }

   public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
	   System.out.println("FactorizationListenerJSON.class- RDFSnapshot is Updated.");
	   	quads = new ArrayList<RdfQuadruple>();
	    mapMeasurement = new HashMap<measurementClass, String>();
	    mapObservation = new HashMap<observationClass, String>();
     // final List<RdfQuadruple> quads = new ArrayList<RdfQuadruple>();

      if (newEvents == null)
    	  return;
      
      for (final EventBean b : newEvents) {
       
			//System.out.println("The Message is :" +b);
		  try {
				JSONArray jsonArray = new JSONArray(b.get("JSONString").toString());
				systemTime=Long.parseLong(b.get("timestamp").toString());
				process(jsonArray,b.get("streamName").toString());
				
			} catch (JSONException | ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  
    	  
//         final RdfQuadruple q = new RdfQuadruple(b.get("subject").toString(), b.get(
//               "predicate").toString(), b.get("object").toString(), Long.parseLong(b.get("timestamp").toString()));

//         q.setStreamName(b.get("streamName").toString());
//         
//         quads.add(q);
      }
      
      setChanged();
      this.notifyObservers(quads);
   }
   
	public void process(final JSONArray jsonarray, String streamName) throws JSONException, ParseException, IOException {
		// JSONArray array;
		//systemTime = System.currentTimeMillis();
		// array = new JSONArray(jsonString);
		if (jsonarray != null && jsonarray.length() > 0) {
			/* check if we need to create molecules or we need to semantify only */
			if (jsonarray.getJSONObject(0).has("observation")) {
				/* create molecules and semantify */

				factorize(jsonarray,streamName);
				if (jsonarray.getJSONObject(0).length() > 7) {
					semantify(jsonarray,streamName);
				}// End of if(jsonarray.getJSONObject(0).length()>7)

			} else {
				/* / semantify only */
				semantify(jsonarray,streamName);
			}
		} else {
			// System.out.println(jsonarray);

		}
	}// end of process()

	public void factorize(final JSONArray jsonArray,String streamName) throws JSONException, ParseException, IOException {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			createMolecules(json,streamName);
		}// end of for (int i = 0; i < array.length(); i++)
	}// end of factorize()

	//////////////////////// Semantify//////////////////////////////////////////////
	public void semantify(final JSONArray jsonArray,String streamName) throws JSONException {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Iterator<?> keys = json.keys();
		//	final RdfQuadruple q;
			while (keys.hasNext()) {
				String key = (String) keys.next();
				switch (key) {
				case "samplingTime":
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("observation"),
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key,
							"http://knoesis.wright.edu/ssw/" + json.getString(key), systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("observation") +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key +
					// " http://knoesis.wright.edu/ssw/" + json.getString(key));
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString(key), "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
							"http://www.w3.org/2006/time#Instant", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString(key) + " http://www.w3.org/1999/02/22-rdf-syntax-ns#type" + " http://www.w3.org/2006/time#Instant");
					break;
				case "inXSDDateTime":
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("samplingTime"), "http://www.w3.org/2006/time#" + key,
							json.getDouble(key) + "^^http://www.w3.org/2001/XMLSchema#dateTime", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("samplingTime") + " http://www.w3.org/2006/time# " + key +
					// json.getDouble(key) + "^^http://www.w3.org/2001/XMLSchema#dateTime");
					break;
				case "ID":
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("procedure"),
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key,
							json.getString(key)+ "^^http://www.w3.org/2001/XMLSchema#string", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("procedure") +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key +
					// json.getString(key));
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("procedure"),
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#System", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("procedure") +
					// "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" +
					// "http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#System");
					break;

				case "hasSourceURI":
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("procedure"),
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key,
							json.getString(key), systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("procedure") +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key +
					// json.getString(key));
					break;
				case "alt":
				case "lat":
				case "long":
					String processLocation = "http://knoesis.wright.edu/ssw/point_" + json.getString("ID");
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("procedure"),
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#processLocation",
							processLocation, systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("procedure") +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#processLocation" +
					// processLocation);
					//put(q);

					q = new RdfQuadruple(processLocation,
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
							"http://www.w3.org/2003/01/geo/wgs84_pos#Point", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println(processLocation +
					// " http://www.w3.org/1999/02/22-rdf-syntax-ns#type" +
					// " http://www.w3.org/2003/01/geo/wgs84_pos#Point");
					q = new RdfQuadruple(processLocation,
							"http://www.w3.org/2003/01/geo/wgs84_pos#" + key,
							BigDecimal.valueOf(json.getDouble(key)).toString() + "^^http://www.w3.org/2001/XMLSchema#float", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println(processLocation +
					// " http://www.w3.org/2003/01/geo/wgs84_pos#" + key +
					// BigDecimal.valueOf(json.getDouble(key)).toString() + "^^http://www.w3.org/2001/XMLSchema#float");
					break;
				case "distance":

					String hasLocatedNearRel = "http://knoesis.wright.edu/ssw/LocatedNearRel" + json.getString("ID");
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("procedure"),
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#hasLocatedNearRel",
							hasLocatedNearRel, systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("procedure") +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#hasLocatedNearRel" +
					// hasLocatedNearRel);
					q = new RdfQuadruple(hasLocatedNearRel,
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#LocatedNearRel", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println(hasLocatedNearRel +
					// " http://www.w3.org/1999/02/22-rdf-syntax-ns#type" +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#LocatedNearRel");
					q = new RdfQuadruple(hasLocatedNearRel,
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key,
							BigDecimal.valueOf(json.getDouble("distance")).toString() + "^^http://www.w3.org/2001/XMLSchema#float", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println(hasLocatedNearRel +
					// "http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key +
					// BigDecimal.valueOf(json.getDouble("distance")).toString() + "^^http://www.w3.org/2001/XMLSchema#float");
					break;
				case "hasLocation":
					String hasLocatedNearRel2 = "http://knoesis.wright.edu/ssw/LocatedNearRel" + json.getString("ID");
					q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("procedure"),
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#hasLocatedNearRel",
							hasLocatedNearRel2, systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("procedure") +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#hasLocatedNearRel" +
					// hasLocatedNearRel2);
					q = new RdfQuadruple(hasLocatedNearRel2,
							"http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#LocatedNearRel", systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println(hasLocatedNearRel2 +
					// " http://www.w3.org/1999/02/22-rdf-syntax-ns#type" +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#LocatedNearRel");
					q = new RdfQuadruple(hasLocatedNearRel2,
							"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key,
							json.getString(key), systemTime);
					q.setStreamName(streamName);
					quads.add(q);
					//put(q);
					// System.out.println(hasLocatedNearRel2 +
					// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#" + key +
					// json.getString(key));
					break;

				}// End of switch (key)
			}
		}// end of for (int i = 0; i < array.length(); i++)
	}

	//////////////////////////////////////// createMolecules()/////////////////
	public void createMolecules(final JSONObject json,String streamName) throws JSONException {
		//RdfQuadruple q;
		final measurementClass measurement = new measurementClass(BigDecimal.valueOf(json.getDouble("floatValue")).floatValue(), json.getString("uom"));
		String mURI = mapMeasurement.get(measurement);
		if (mURI != null) {
			final observationClass obs = new observationClass(json.getString("procedure"), json.getString("observedProperty"),
					json.getString("type"), mURI);
			final String obsURI = mapObservation.get(obs);
			if (obsURI != null) {

				q = new RdfQuadruple(obsURI,
						"http://linkeddata.com/ontology#hasObservation",
						"http://knoesis.wright.edu/ssw/" + json.getString("observation"),
						systemTime);
				q.setStreamName(streamName);
				quads.add(q);
				//put(q);
				// System.out.println(obsURI +
				// " http://linkeddata.com/ontology#hasObservation" +
				// " http://knoesis.wright.edu/ssw/" + json.getString("observation"));
				q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("observation"),
						"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result",
						"http://knoesis.wright.edu/ssw/" + json.getString("result"), systemTime);
				q.setStreamName(streamName);
				quads.add(q);
				// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("observation") +
				// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result" +
				// " http://knoesis.wright.edu/ssw/" + json.getString("result"));
				//put(q);
			} // / end of if (obsURI != null)
			else {
				createObservationMolecule(obs, json.getString("observation"),streamName);
				q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("observation"),
						"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result",
						"http://knoesis.wright.edu/ssw/" + json.getString("result"), systemTime);
				q.setStreamName(streamName);
				quads.add(q);
				//put(q);
				// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("observation") +
				// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result" +
				// " http://knoesis.wright.edu/ssw/" + json.getString("result"));
			}
		} // / end of if (mURI != null)
		else {
			mURI = createMeasurmentMolecule(measurement,streamName);
			final observationClass obs = new observationClass(json.getString("procedure"), json.getString("observedProperty"),
					json.getString("type"), mURI);
			createObservationMolecule(obs, json.getString("observation"),streamName);
			q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + json.getString("observation"),
					"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result",
					"http://knoesis.wright.edu/ssw/" + json.getString("result"), systemTime);
			q.setStreamName(streamName);
			quads.add(q);
			//put(q);
			// System.out.println("http://knoesis.wright.edu/ssw/" + json.getString("observation") +
			// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result" +
			// " http://knoesis.wright.edu/ssw/" + json.getString("result"));
		}
	}// End of createMolecules()

	/////////////////////////////////// createMeasurmentMolecule()***////
	public String createMeasurmentMolecule(
			final measurementClass measurement,String streamName) {
		//RdfQuadruple q;
		final String valueStr[] = String.valueOf(measurement.getValue()).split(
				"\\.");
		String new_mURI = "http://linkeddata.com/ontology#"
				+ measurement.getUOM() + valueStr[0]
				+ "DP" + valueStr[1];
		mapMeasurement.put(measurement, new_mURI);
		q = new RdfQuadruple(new_mURI,
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#MeasureData", systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		// System.out.println(new_mURI +
		// "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" +
		// "http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#MeasureData");
		//put(q);
		q = new RdfQuadruple(new_mURI,
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#floatValue",
				measurement.getValue() + "^^http://www.w3.org/2001/XMLSchema#float", systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_mURI +
		// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#floatValue" +
		// measurement.getValue() + "^^http://www.w3.org/2001/XMLSchema#float");
		q = new RdfQuadruple(new_mURI,
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#uom",
				"http://knoesis.wright.edu/ssw/ont/weather.owl#" + measurement.getUOM(), systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_mURI +
		// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#uom" +
		// " http://knoesis.wright.edu/ssw/ont/weather.owl#" + measurement.getUOM());
		return new_mURI;
	}

	// ///////////////////////////////////////////////////////////////////////////

	public void createObservationMolecule(final observationClass obs,
			final String observation,String streamName) {
		//RdfQuadruple q;
		// final long startTime = System.currentTimeMillis();
		String new_obsURI = "http://linkeddata.com/ontology#"
				+ observation;
		mapObservation.put(obs, new_obsURI);
		q = new RdfQuadruple(new_obsURI,
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result",
				obs.getmURI(), systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_obsURI +
		// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#result " +
		// obs.getmURI());
		q = new RdfQuadruple(new_obsURI,
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
				"http://knoesis.wright.edu/ssw/ont/weather.owl#" + obs.getType(), systemTime);
							q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_obsURI +
		// " http://www.w3.org/1999/02/22-rdf-syntax-ns#type " +
		// "http://knoesis.wright.edu/ssw/ont/weather.owl#" + obs.getType());
		q = new RdfQuadruple(new_obsURI,
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#observedProperty",
				"http://knoesis.wright.edu/ssw/ont/weather.owl#" + obs.getProperty(), systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_obsURI +
		// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#observedProperty " +
		// "http://knoesis.wright.edu/ssw/ont/weather.owl#" + obs.getProperty());
		q = new RdfQuadruple(new_obsURI,
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#procedure",
				"http://knoesis.wright.edu/ssw/" + obs.getProcedure(), systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_obsURI +
		// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#procedure " +
		// "http://knoesis.wright.edu/ssw/" + obs.getProcedure());
		q = new RdfQuadruple("http://knoesis.wright.edu/ssw/" + obs.getProcedure(),
				"http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#generatedObservation",
				new_obsURI, systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println("http://knoesis.wright.edu/ssw/" + obs.getProcedure() +
		// " http://knoesis.wright.edu/ssw/ont/sensor-observation.owl#generatedObservation " +
		// new_obsURI);
		q = new RdfQuadruple(new_obsURI,
				"http://linkeddata.com/ontology#hasObservation",
				"http://knoesis.wright.edu/ssw/" + observation, systemTime);
		q.setStreamName(streamName);
		quads.add(q);
		//put(q);
		// System.out.println(new_obsURI +
		// " http://linkeddata.com/ontology#hasObservation " +
		// "http://knoesis.wright.edu/ssw/" + observation);
	}
}