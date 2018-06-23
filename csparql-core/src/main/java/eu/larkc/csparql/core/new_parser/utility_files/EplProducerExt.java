/**
 * @Author- Farah Karim
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.larkc.csparql.core.new_parser.utility_files;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import eu.larkc.csparql.cep.api.JSONStream;
import eu.larkc.csparql.core.engine.CsparqlEngineExt;
import eu.larkc.csparql.core.streams.formats.TranslationException;

/**
 * Allow to build one or more executable EPL queries (one for each stream) from a CSparql
 * one.
 * 
 * @author Marco
 */
public class EplProducerExt {

	private CsparqlEngineExt engine;
	private ArrayList<StreamInfo> streams;

	public EplProducerExt(CsparqlEngineExt engine, ArrayList<StreamInfo> streams) {
		super();
		this.engine = engine;
		this.streams = streams;
	}

	public String convertStreamIri(final String uri) throws TranslationException {

		JSONStream s = this.engine.getStreamByIri(uri);
		if (s == null)
		{
			throw new TranslationException("IRI " + uri  + " not found.");
		}

		return s.uniqueName();
	}

	public Set<String> produceEpl() throws TranslationException {
		
		Set<String> result = new HashSet<String>();

		for (StreamInfo si : streams) {
			final StringBuffer s = new StringBuffer(
					"select irstream * from ");
			String iri = si.getIri();
			// Apply the name conversion

			if (iri == null)
			{
				TranslationException e = new TranslationException("Stream IRI " + iri + " not found");
				e.setIri(iri);
				throw e;
			}
			s.append(this.convertStreamIri(iri));
			s.append('.');
			System.out.println("iri is:"+iri);
			final Window w = si.getWindow();
			if (si.hasPhisicalWindow()) {
				final PhysicalWindow ph = (PhysicalWindow) w;
				s.append("win:length_batch(");
				s.append(ph.getWindowRange());
				s.append(")");
				s.append(" output snapshot every ");
				s.append(ph.getWindowRange());
				s.append(" events");
			} else {
				final LogicalWindow l = (LogicalWindow) w;
				final String range = TimeUtils.getSeconds(l.getRangeDescription().getValue(), l.getRangeDescription().getTimeUnit());
				// Tumbling
				if (l.isTumbling()) {
					s.append("win:time_batch(");
					// add range
					s.append(range);
					s.append(")");
				} else {
					s.append("win:time(");
					// add range
					s.append(range);
					s.append(")");
					s.append(" output snapshot every ");
					// ass step
					final String step = TimeUtils.getSeconds(l.getStepDescription().getValue(), l.getStepDescription().getTimeUnit());
					s.append(step);
				}
			}
			System.out.println("Stream is: "+s.toString());
			result.add(s.toString());
		}
		return result;
	}
}
