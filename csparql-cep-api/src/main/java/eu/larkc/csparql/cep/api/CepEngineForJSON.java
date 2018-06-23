/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.cep.api;

import java.util.Collection;
import java.util.Observer;

public interface CepEngineForJSON extends Observer {

   /**
   */
   void registerStream(JSONStream stream);

   /**
    */
   void unregisterStream(JSONStream stream);

   /**
   */
   void initialize();
   
   /**
   */
   void unregisterQuery(String id);

   /**
   */
   Collection<JSONStream> getAllRegisteredStreams();

   void destroy();

   /**
   */
   Collection<CepQuery> getAllQueries();

   RdfSnapshot registerQuery(String query, String id);

   void startQuery(String id);

   void stopQuery(String id);
   
   String getCepEngineType();

   Long setCurrentTimeAndSentEvent(JSONData j);

   Long getCurrentTime();
}
