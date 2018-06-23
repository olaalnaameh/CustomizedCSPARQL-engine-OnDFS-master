/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.core.engine;

import java.text.ParseException;
import java.util.Collection;

//import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.utils.ReasonerChainingType;
import eu.larkc.csparql.core.streams.formats.CSparqlQuery;

import eu.larkc.csparql.cep.api.JSONStream;

public interface CsparqlEngineExt {

	/**
	 */
//	CsparqlQueryResultProxy registerQuery(String command) throws ParseException;

	CsparqlQueryResultProxy registerQuery(String command, boolean activateInference) throws ParseException;
	CsparqlQueryResultProxy registerQuery(String command, boolean activateInference, String rulesFileSerialization, ReasonerChainingType chainingType) throws ParseException;
	CsparqlQueryResultProxy registerQuery(String command, boolean activateInference, String rulesFileSerialization,	ReasonerChainingType chainingType, String tBoxFileSerialization) throws ParseException;
    CsparqlQueryResultProxy registerQuery(String command, boolean activateInference, String tBoxFileSerialization) throws ParseException;

	/**
	 */
	void initialize();

	void initialize(int queueDimension);

	void initialize(boolean performTimestampFunction);

	void initialize(int queueDimension, boolean performTimestampFunction);

	void execUpdateQueryOverDatasource(String queryBody);

	void putStaticNamedModel(String iri, String modelReference);

	void removeStaticNamedModel(String iri);

	void destroy();

	/**
	 */
	//RdfStream registerStream(RdfStream stream);
	/// Added
	JSONStream registerStream(JSONStream stream);
	/**
	 */
	Collection<CSparqlQuery> getAllQueries();

	/**
	 */
	void unregisterQuery(String id);

	/**
	 */
	void unregisterStream(String iri);

	void startQuery(final String id);

	void stopQuery(final String id);

	//RdfStream getStreamByIri(String iri);
	
	/// Added
	JSONStream getStreamByIri(String iri);

//	void activateInference();
//
//	void activateInference(String rulesFilePath, String entailmentRegimeType);
//
//	void activateInference(String rulesFilePath, String entailmentRegimeType, String tBoxFilePath);
	
	void arrestInference(String queryId);
	void restartInference(String queryId);
	
	void updateReasoner(String queryId);
	void updateReasoner(String queryId, String rulesFile, ReasonerChainingType chainingType);
	void updateReasoner(String queryId, String rulesFile, ReasonerChainingType chainingType, String tBoxFile);

	boolean getInferenceStatus();

	RDFTable evaluateGeneralQueryOverDatasource(String queryBody);



}
