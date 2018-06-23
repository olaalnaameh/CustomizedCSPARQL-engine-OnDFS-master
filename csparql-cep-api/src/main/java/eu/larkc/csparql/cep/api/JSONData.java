/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.cep.api;

public class JSONData {

   //private final String subject;
   //private final String predicate;
   //private final String object;
private final String JSONString;
   private final long timestamp;
   private String streamName;
   
   public JSONData(final String JSONString, final long milliseconds) {
      this.JSONString = JSONString;
      this.timestamp = milliseconds;
   }

   public String getJSONString() {
      return this.JSONString;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   @Override
	public String toString() {
		return getJSONString() + " . (" + getTimestamp() + ")";
	}



public void setStreamName(String streamName) {
	this.streamName = streamName;
}



public String getStreamName() {
	return streamName;
}
}
