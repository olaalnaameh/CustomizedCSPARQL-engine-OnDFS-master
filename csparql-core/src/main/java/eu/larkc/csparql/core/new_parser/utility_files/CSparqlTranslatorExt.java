/**
 * @Author- Farah Karim
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.larkc.csparql.core.new_parser.utility_files;

//Importazioni manuali aggiuntive
import java.util.Set;

import eu.larkc.csparql.core.new_parser.CsparqlParser;
import eu.larkc.csparql.core.new_parser.FactorizedCsparqlParser;
import eu.larkc.csparql.core.new_parser.ParseException;
import eu.larkc.csparql.core.streams.formats.CSparqlQuery;
import eu.larkc.csparql.core.streams.formats.CSparqlQueryImpl;
import eu.larkc.csparql.core.streams.formats.TranslationException;

/**
 * @author Marco Regaldo
 */
public final class CSparqlTranslatorExt extends TranslatorExt {

	public CSparqlTranslatorExt() {
	}

	@Override
	public CSparqlQuery translate(String queryCommand) throws ParseException, TranslationException{
		System.out.println("Input queryCommand is: "+queryCommand);
		//CsparqlParser parser = CsparqlParser.createAndParse(queryCommand);
		/// Added
		FactorizedCsparqlParser parser = FactorizedCsparqlParser.createAndParse(queryCommand);
		///
		System.out.println("Parsed queryCommand is: "+parser.getSparqlQuery());
		EplProducerExt ep = new EplProducerExt(this.getEngine(), parser.getStreams());
		Set<String> epls = ep.produceEpl();
		CSparqlQuery csq = new CSparqlQueryImpl(epls.toArray()[0].toString(), parser.getSparqlQuery(), queryCommand, parser.getStreams());
		return csq;	   

	}

	//   @Override
	//   public CSparqlQuery translate(final String queryCommand) throws TranslationException {
	//
	//      try {
	//         final TreeBox tb = CSparqlTranslator.parse(queryCommand);
	//         final EplProducer1_0 ep = new EplProducer1_0(this.getEngine());
	//         final SparqlProducer1_0 sp = new SparqlProducer1_0();
	//         final Set<String> epls = ep.produceEpl(tb);
	//         final String spq = sp.produceSparql(tb);
	//         CSparqlQuery csq = new CSparqlQueryImpl(epls.toArray()[0].toString(), spq, queryCommand);
	//         csq.setTreeBox(tb);
	//         return csq;
	//      } catch (final RecognitionException e) {
	//         throw new TranslationException("Incorrect Syntax near \"" + e.token.getText()
	//               + "\"");
	//      } catch (final PostProcessingException e) {
	//         throw new TranslationException(e.getMessage());
	//      }
	//   }
}
