/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import eu.larkc.csparql.cep.api.CepEngineForJSON;
import eu.larkc.csparql.cep.api.CepQuery;
//import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlEngineExt;
import eu.larkc.csparql.core.engine.Reasoner;
import eu.larkc.csparql.core.new_parser.utility_files.TranslatorExt;
import eu.larkc.csparql.sparql.api.SparqlEngine;
import eu.larkc.csparql.sparql.api.SparqlQuery;

public class ConfigurationExt {
	
   // Singleton pattern implementation
   private static ConfigurationExt instance;
   
   private final String cepEngineName = "eu.larkc.csparql.cep.esper.EsperEngineForJSON";
   private final String reasonerName = "eu.larkc.csparql.core.engine.TransparentReasoner";
   private final String sparqlEngineName = "eu.larkc.csparql.sparql.jena.JenaEngine";
   private final String cepQueryName = "eu.larkc.csparql.cep.esper.EsperQuery";
   private final String sparqlQueryName = "eu.larkc.csparql.sparql.sesame.SesameQuery";
   private final String translatorName = "eu.larkc.csparql.core.new_parser.utility_files.CSparqlTranslatorExt";

   public static ConfigurationExt getCurrentConfiguration() {
      if (ConfigurationExt.instance == null) {
         ConfigurationExt.instance = new ConfigurationExt();
      }

      return ConfigurationExt.instance;
   }

   public Reasoner createReasoner() {

      Class< ? > c = null;

      try {
         c = Class.forName(this.reasonerName);

         return (Reasoner) c.newInstance();

      } catch (final IllegalAccessException ex) {
         ex.printStackTrace();
         return null;
      } catch (final ClassNotFoundException ex) {
         ex.printStackTrace();
         return null;
      } catch (final InstantiationException ex) {
         ex.printStackTrace();
         return null;
      } catch (final IllegalArgumentException ex) {
         // TODO Auto-generated catch block
         ex.printStackTrace();
      }

      return null;
   }

   public TranslatorExt createTranslator(final CsparqlEngineExt engine) {

      Class< ? > c = null;

      try {
         c = Class.forName(this.translatorName);

         final TranslatorExt t = (TranslatorExt) c.newInstance();
         t.setEngine(engine);
         return t;

      } catch (final IllegalAccessException ex) {
         ex.printStackTrace();
         return null;
      } catch (final ClassNotFoundException ex) {
         ex.printStackTrace();
         return null;
      } catch (final InstantiationException ex) {
         ex.printStackTrace();
         return null;
      } catch (final IllegalArgumentException ex) {
         // TODO Auto-generated catch block
         ex.printStackTrace();
      }

      return null;
   }

   public CepQuery createCepQuery(final String command) {
      // TODO: Correct it, it should NEVER return null!
      Class< ? > c = null;
      CepQuery e = null;

      try {
         c = Class.forName(this.cepQueryName);

         final Constructor< ? >[] ctors = c.getConstructors();
         e = (CepQuery) ctors[0].newInstance(command);
         return e;

      } catch (final IllegalAccessException ex) {
         ex.printStackTrace();
         return null;
      } catch (final ClassNotFoundException ex) {
         ex.printStackTrace();
         return null;
      } catch (final InstantiationException ex) {
         ex.printStackTrace();
         return null;
      } catch (final IllegalArgumentException ex) {
         // TODO Auto-generated catch block
         ex.printStackTrace();
      } catch (final InvocationTargetException ex) {
         // TODO Auto-generated catch block
         ex.printStackTrace();
      }

      return null;
   }

   public SparqlQuery createSparqlQuery(final String command) {
      // TODO: Correct it, it should NEVER return null!
      Class< ? > c = null;
      SparqlQuery e = null;

      try {
         c = Class.forName(this.sparqlQueryName);

         final Constructor< ? >[] ctors = c.getConstructors();

         for (final Constructor< ? > cc : ctors) {

            if (cc.getParameterTypes().length == 1) {
               e = (SparqlQuery) cc.newInstance(command);
               return e;
            }
         }

      } catch (final IllegalAccessException ex) {
         ex.printStackTrace();
         return null;
      } catch (final ClassNotFoundException ex) {
         ex.printStackTrace();
         return null;
      } catch (final InstantiationException ex) {
         ex.printStackTrace();
         return null;
      } catch (final IllegalArgumentException ex) {
         // TODO Auto-generated catch block
         ex.printStackTrace();
      } catch (final InvocationTargetException ex) {
         // TODO Auto-generated catch block
         ex.printStackTrace();
      }

      return null;
   }

   public CepEngineForJSON createCepEngine() {
      // TODO: Correct it, it should NEVER return null!
      Class< ? > c = null;
      CepEngineForJSON e = null;

      try {
         c = Class.forName(this.cepEngineName);
         e = (CepEngineForJSON) c.newInstance();
         return e;
      } catch (final IllegalAccessException ex) {
         ex.printStackTrace();
         return null;
      } catch (final ClassNotFoundException ex) {
         ex.printStackTrace();
         return null;
      } catch (final InstantiationException ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public SparqlEngine createSparqlEngine() {
      // TODO: Correct it, it should NEVER return null!
      Class< ? > c = null;
      SparqlEngine e = null;

      try {
         c = Class.forName(this.sparqlEngineName);
         e = (SparqlEngine) c.newInstance();
         return e;
      } catch (final IllegalAccessException ex) {
         ex.printStackTrace();
         return null;
      } catch (final ClassNotFoundException ex) {
         ex.printStackTrace();
         return null;
      } catch (final InstantiationException ex) {
         ex.printStackTrace();
         return null;
      }
   }

}
