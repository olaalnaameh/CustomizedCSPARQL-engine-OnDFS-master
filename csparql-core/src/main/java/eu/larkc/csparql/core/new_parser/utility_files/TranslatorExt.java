/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.core.new_parser.utility_files;

import eu.larkc.csparql.core.engine.CsparqlEngineExt;
import eu.larkc.csparql.core.streams.formats.CSparqlQuery;

public abstract class TranslatorExt {

   private CsparqlEngineExt engine = null;

   public abstract CSparqlQuery translate(String queryCommand) throws Exception;

   public CsparqlEngineExt getEngine() {
      return this.engine;
   }

   public void setEngine(final CsparqlEngineExt engine) {
      this.engine = engine;
   }
}
