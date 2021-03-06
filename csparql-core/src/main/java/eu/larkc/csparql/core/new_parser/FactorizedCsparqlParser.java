package eu.larkc.csparql.core.new_parser ;

import eu.larkc.csparql.core.new_parser.CsparqlParserBase;
import eu.larkc.csparql.core.new_parser.CsparqlParserConstants;
import java.io.StringReader;
import java.util.Set;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashSet;
import eu.larkc.csparql.core.new_parser.utility_files.*;
public class FactorizedCsparqlParser extends CsparqlParserBase implements CsparqlParserConstants {
	private static int filter_count=0;
	private static boolean filter_flag=false;
	private String sparqlQueryTranslated="PREFIX f: <http://fact.com/onto#> ";
	//private static String sparqlQueryTranslated="PREFIX f:   <http://linkeddata.com/ontology#> ";
	private static boolean moleculeOn =false;
	private static boolean previousOn =false;
	private static String endSymbol="";
	private static String sqlquery ="";	   
	private static String required_var="";
	private static String where_condition="";
	private static Set<String> moleculeTypeSet = new HashSet();
	private static Set<String> required_varSet = new HashSet();
	private boolean requiredVarOn=false;
	private String timeunit = "";
	private int value;
	
	public static FactorizedCsparqlParser createAndParse(String queryString) throws ParseException{
		FactorizedCsparqlParser parser = new FactorizedCsparqlParser(new StringReader(TextUtilities.queryTextEnhancer(queryString)));
//	    System.out.println(TextUtilities.queryTextEnhancer(queryString));
	    parser.Query();
//	    System.out.println();
//	    System.out.println("Translated Query From FactorizedCsparqlParser:");
//	    System.out.println(sparqlQueryTranslated);
//	    System.out.println();
	    sqlquery=" SELECT "+ required_var +" FROM OBSERVATIONSTABLE WHERE ("+where_condition+")";
	    System.out.println("SQL Query From FactorizedCsparqlParser:");
	    System.out.println(sqlquery);
	    try {
				System.out.println("Open Websocket");
		// open websocket
			WebsocketSQLClientEndpoint clientEndPoint = new WebsocketSQLClientEndpoint(new URI("ws://127.0.0.1:1880/mywebsocket"));
			System.out.println("Websocket is opened");
		// send message to websocket
		clientEndPoint.sendMessage(sqlquery);
			System.out.println("SQL Query sent to NodeRed");
		// wait 5 seconds for messages from websocket
		// Thread.sleep(0);
	}// End of try
	catch (URISyntaxException ex) {
		System.err.println("URISyntaxException exception: " + ex.getMessage());
	}// ENd of catch (URISyntaxException ex)
	    
	    
	    //Passing the WINDOW Size
	    
	    try {
			System.out.println("Open Websocket for WINDOW Size");
	// open websocket
		WebsocketSQLClientEndpoint clientEndPoint = new WebsocketSQLClientEndpoint(new URI("ws://127.0.0.1:1880/mywebsocket"));
		System.out.println("Websocket is opened");
	// send message to websocket
	clientEndPoint.sendMessage(sqlquery);
		System.out.println("SQL Query sent to NodeRed");
	// wait 5 seconds for messages from websocket
	// Thread.sleep(0);
}// End of try
catch (URISyntaxException ex) {
	System.err.println("URISyntaxException exception: " + ex.getMessage());
}// ENd of catch (URISyntaxException ex)
		 
	    return parser;
	  }
	
	final public void Query() throws ParseException {
	    RegisterClause();
	    label_1:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case PREFIX:
	      case BASE:
	        ;
	        break;
	      default:
	        jj_la1[0] = jj_gen;
	        break label_1;
	      }
	      //System.out.println("1: goto Prologue()");
	      Prologue();
	    }
	    if (jj_2_1(5)) {
	    	//System.out.println("2: goto SelectClause()");
	      SelectClause();
	    } else {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case CONSTRUCT:
	    	  //System.out.println("3: goto constructClause()");
	        ConstructClause();
	        break;
	      case DESCRIBE:
	    	  //System.out.println("4: goto describeClause()");
	        DescribeClause();
	        break;
	      case ASK:
	    	  //System.out.println("5: gotoAskClause()");
	        AskClause();
	        break;
	      default:
	        jj_la1[1] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	    }
	    label_2:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case FROM:
	        ;
	        break;
	      default:
	        jj_la1[2] = jj_gen;
	        break label_2;
	      }
	      if (jj_2_2(5)) {
	    	  //System.out.println("6: goto FromClause()");
	        FromClause();
	      } else {
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case FROM:
	        	//System.out.println("7: FromStreamClause()");
	          FromStreamClause();
	          break;
	        default:
	          jj_la1[3] = jj_gen;
	          jj_consume_token(-1);
	          throw new ParseException();
	        }
	      }
	    }
	    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	    case WHERE:
	    	//System.out.println("8: goto WhereClause()");
	      WhereClause();
	      break;
	    default:
	      jj_la1[4] = jj_gen;
	      ;
	    }
	  //setSparqlQuery(sparqlQuery) ;
	  setSparqlQuery(sparqlQueryTranslated);
	    jj_consume_token(0);
	  }

	  final public void Prologue() throws ParseException {
	                    Token t;
	    if (jj_2_4(2)) {
	      t = jj_consume_token(PREFIX);
	                                sparqlQuery = sparqlQuery + t.image ;
	                      sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                
	      label_3:
	      while (true) {
	        if (jj_2_3(2)) {
	          t = jj_consume_token(SINGLELETTERTEXT);
	                                   sparqlQuery = sparqlQuery + t.image ;
	                                   sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	        } else {
	          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	          case NUMBERS:
	            t = jj_consume_token(NUMBERS);
	                                          sparqlQuery = sparqlQuery + t.image ;
	                                          sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	            break;
	          case SYMBOL:
	            t = jj_consume_token(SYMBOL);
	                           sparqlQuery = sparqlQuery + t.image ;
	                           sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	            break;
	          default:
	            jj_la1[5] = jj_gen;
	            jj_consume_token(-1);
	            throw new ParseException();
	          }
	        }
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SINGLELETTERTEXT:
	        case NUMBERS:
	        case SYMBOL:
	          ;
	          break;
	        default:
	          jj_la1[6] = jj_gen;
	          break label_3;
	        }
	      }
	      label_4:
	      while (true) {
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SPACE:
	          ;
	          break;
	        default:
	          jj_la1[7] = jj_gen;
	          break label_4;
	        }
	        t = jj_consume_token(SPACE);
	                               sparqlQuery = sparqlQuery + t.image ;
	                               sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	      }
	      t = jj_consume_token(IRIref);
	                                 sparqlQuery = sparqlQuery + t.image ;
	                                 sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	    } else {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case BASE:
	        t = jj_consume_token(BASE);
	                     sparqlQuery = sparqlQuery + t.image ;
	                     sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	        t = jj_consume_token(IRIref);
	                                 sparqlQuery = sparqlQuery + t.image ;
	                                 sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	        break;
	      default:
	        jj_la1[8] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	    }
	    label_5:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[9] = jj_gen;
	        break label_5;
	      }
	      t = jj_consume_token(SPACE);
	                 sparqlQuery = sparqlQuery + t.image ;
	                 sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	    sparqlQueryTranslated = sparqlQueryTranslated + "\u005cn";
	  }

	  final public void RegisterClause() throws ParseException {
	                           Token t ; String queryName ="";
	    if (jj_2_5(3)) {
	      jj_consume_token(REGISTERSTREAM);
	                       setOutputIsStream(true);
	    } else {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case REGISTERQUERY:
	        jj_consume_token(REGISTERQUERY);
	                       setOutputIsStream(false);
	        break;
	      default:
	        jj_la1[10] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	    }
	    label_6:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SINGLELETTERTEXT:
	        t = jj_consume_token(SINGLELETTERTEXT);
	                              queryName = queryName + t.image ;
	        break;
	      case NUMBERS:
	        t = jj_consume_token(NUMBERS);
	                                                                                    queryName = queryName + t.image ;
	        break;
	      default:
	        jj_la1[11] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SINGLELETTERTEXT:
	      case NUMBERS:
	        ;
	        break;
	      default:
	        jj_la1[12] = jj_gen;
	        break label_6;
	      }
	    }
	    jj_consume_token(SPACE);
	    jj_consume_token(AS);
	          setCsparqlQueryName(queryName.trim());
	    label_7:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[13] = jj_gen;
	        break label_7;
	      }
	      jj_consume_token(SPACE);
	    }
	  }

	  final public void AddClause() throws ParseException {
	                     Token t;
	    t = jj_consume_token(ADD);
	              sparqlQuery = sparqlQuery + t.image ;
	    label_8:
	    while (true) {
	      label_9:
	      while (true) {
	        if (jj_2_6(2)) {
	          t = jj_consume_token(AS);
	               sparqlQuery = sparqlQuery + t.image ;
	        } else {
	          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	          case SINGLELETTERTEXT:
	            t = jj_consume_token(SINGLELETTERTEXT);
	                               sparqlQuery = sparqlQuery + t.image ;
	            break;
	          case SYMBOL:
	            t = jj_consume_token(SYMBOL);
	                     sparqlQuery = sparqlQuery + t.image ;
	            break;
	          case NUMBERS:
	            t = jj_consume_token(NUMBERS);
	                      sparqlQuery = sparqlQuery + t.image ;
	            break;
	          default:
	            jj_la1[14] = jj_gen;
	            jj_consume_token(-1);
	            throw new ParseException();
	          }
	        }
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SINGLELETTERTEXT:
	        case NUMBERS:
	        case SYMBOL:
	        case AS:
	          ;
	          break;
	        default:
	          jj_la1[15] = jj_gen;
	          break label_9;
	        }
	      }
	      t = jj_consume_token(SPACE);
	                  sparqlQuery = sparqlQuery + t.image ;
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SINGLELETTERTEXT:
	      case NUMBERS:
	      case SYMBOL:
	      case AS:
	        ;
	        break;
	      default:
	        jj_la1[16] = jj_gen;
	        break label_8;
	      }
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	  }

	  final public void DescribeClause() throws ParseException {
	                          Token t;
	    t = jj_consume_token(DESCRIBE);
	                   sparqlQuery = sparqlQuery + t.image ;
	    label_10:
	    while (true) {
	      label_11:
	      while (true) {
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case ADD:
	          t = jj_consume_token(ADD);
	                sparqlQuery = sparqlQuery + t.image ;
	          break;
	        case CONSTRUCT:
	          t = jj_consume_token(CONSTRUCT);
	                      sparqlQuery = sparqlQuery + t.image ;
	          break;
	        case SELECT:
	          t = jj_consume_token(SELECT);
	                   sparqlQuery = sparqlQuery + t.image ;
	          break;
	        case AS:
	          t = jj_consume_token(AS);
	                                                                        sparqlQuery = sparqlQuery + t.image ;
	          break;
	        case SINGLELETTERTEXT:
	          t = jj_consume_token(SINGLELETTERTEXT);
	                             sparqlQuery = sparqlQuery + t.image ;
	          break;
	        case SYMBOL:
	          t = jj_consume_token(SYMBOL);
	                   sparqlQuery = sparqlQuery + t.image ;
	          break;
	        case NUMBERS:
	          t = jj_consume_token(NUMBERS);
	                    sparqlQuery = sparqlQuery + t.image ;
	          break;
	        default:
	          jj_la1[17] = jj_gen;
	          jj_consume_token(-1);
	          throw new ParseException();
	        }
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SELECT:
	        case ADD:
	        case CONSTRUCT:
	        case SINGLELETTERTEXT:
	        case NUMBERS:
	        case SYMBOL:
	        case AS:
	          ;
	          break;
	        default:
	          jj_la1[18] = jj_gen;
	          break label_11;
	        }
	      }
	      t = jj_consume_token(SPACE);
	                sparqlQuery = sparqlQuery + t.image ;
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SELECT:
	      case ADD:
	      case CONSTRUCT:
	      case SINGLELETTERTEXT:
	      case NUMBERS:
	      case SYMBOL:
	      case AS:
	        ;
	        break;
	      default:
	        jj_la1[19] = jj_gen;
	        break label_10;
	      }
	    }
	    label_12:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[20] = jj_gen;
	        break label_12;
	      }
	      t = jj_consume_token(SPACE);
	                  sparqlQuery = sparqlQuery + t.image ;
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	  }

	  final public void AskClause() throws ParseException {
	                     Token t;
	    t = jj_consume_token(ASK);
	              sparqlQuery = sparqlQuery + t.image ;
	              sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	    sparqlQuery = sparqlQuery + "\u005cn";
	  }

	  final public void SelectClause() throws ParseException {
	                        Token t;
	    t = jj_consume_token(SELECT);
	                 sparqlQuery = sparqlQuery + t.image ;
	                 sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	    label_13:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SINGLELETTERTEXT:
	      case NUMBERS:
	      case SYMBOL:
	      case IRIref:
	      case SPACE:
	      case AS:
	        ;
	        break;
	      default:
	        jj_la1[21] = jj_gen;
	        break label_13;
	      }
	      if (jj_2_7(3)) {
	        t = jj_consume_token(SINGLELETTERTEXT);
	                                       sparqlQuery = sparqlQuery + t.image ;
	                                       sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	      } else {
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SYMBOL:
	          t = jj_consume_token(SYMBOL);
	                               sparqlQuery = sparqlQuery + t.image ;
	                               sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	          break;
	        case NUMBERS:
	          t = jj_consume_token(NUMBERS);
	                                sparqlQuery = sparqlQuery + t.image ;
	                                sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	          break;
	        case IRIref:
	          t = jj_consume_token(IRIref);
	                                 sparqlQuery = sparqlQuery + t.image ;
	                                 sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	          break;
	        case SPACE:
	          t = jj_consume_token(SPACE);
	                              sparqlQuery = sparqlQuery + t.image ;
	                              sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	          break;
	        case AS:
	          t = jj_consume_token(AS);
	                           sparqlQuery = sparqlQuery + t.image ;
	                           sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	          break;
	        default:
	          jj_la1[22] = jj_gen;
	          jj_consume_token(-1);
	          throw new ParseException();
	        }
	      }
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	    sparqlQueryTranslated = sparqlQueryTranslated + "\u005cn";
	  }

	  final public void ConstructClause() throws ParseException {
	                           Token t;
	    t = jj_consume_token(CONSTRUCT);
	                      sparqlQuery = sparqlQuery + t.image ;
	                      sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                     // System.out.println(" at 1: "+t.image);
	    label_14:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SINGLELETTERTEXT:
	      case NUMBERS:
	      case SYMBOL:
	      case IRIref:
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[23] = jj_gen;
	        break label_14;
	      }
	      if (jj_2_8(3)) {
	        t = jj_consume_token(SINGLELETTERTEXT);
	                                       sparqlQuery = sparqlQuery + t.image ;
	                                       sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                       //System.out.println(" at 2: "+t.image);
	      } else {
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SYMBOL:
	          t = jj_consume_token(SYMBOL);
	                               sparqlQuery = sparqlQuery + t.image ;
	                               sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                               //System.out.println(" at 3: "+t.image);
	          break;
	        case NUMBERS:
	          t = jj_consume_token(NUMBERS);
	                                sparqlQuery = sparqlQuery + t.image ;
	                                sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                //System.out.println(" at 4: "+t.image);
	          break;
	        case IRIref:
	          t = jj_consume_token(IRIref);
	                                 sparqlQuery = sparqlQuery + t.image ;
	                                 sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                 //System.out.println(" at 5: "+t.image);
	          break;
	        case SPACE:
	          t = jj_consume_token(SPACE);
	                              sparqlQuery = sparqlQuery + t.image ;
	                              sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                              //System.out.println(" at 6: "+t.image);
	          break;
	        default:
	          jj_la1[24] = jj_gen;
	          jj_consume_token(-1);
	          throw new ParseException();
	        }
	      }
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	    sparqlQueryTranslated = sparqlQueryTranslated + "\u005cn";
	  }

	  final public void FromClause() throws ParseException {
	                      Token t;
	    t = jj_consume_token(FROM);
	               sparqlQuery = sparqlQuery + t.image ;
	               sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	    //t = " " { s = s + t.image ; }
	      //((t = <SINGLELETTERTEXT> { s = s + t.image ; } | t = <SYMBOL> { s = s + t.image ; })+ t = " " { s = s + t.image ; })+
	       t = jj_consume_token(IRIref);
	                    sparqlQuery = sparqlQuery + t.image ;
	                    sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	    label_15:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[25] = jj_gen;
	        break label_15;
	      }
	      t = jj_consume_token(SPACE);
	                  sparqlQuery = sparqlQuery + t.image ;
	                  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	    sparqlQueryTranslated = sparqlQueryTranslated + "\u005cn";
	  }

	  final public void FromStreamClause() throws ParseException {
	                             Token t ; String streamName =""; String s1 ="";
	    Window window;
	    jj_consume_token(FROM);
	    label_16:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case NAMED:
	        ;
	        break;
	      default:
	        jj_la1[26] = jj_gen;
	        break label_16;
	      }
	      jj_consume_token(NAMED);
	    }
	    jj_consume_token(STREAM);
	    t = jj_consume_token(IRIref);
	                     streamName = t.image ;
	    jj_consume_token(SPACE);
	    label_17:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[27] = jj_gen;
	        break label_17;
	      }
	      jj_consume_token(SPACE);
	    }
	    jj_consume_token(RANGE);
	    window = Window();
	    jj_consume_token(SYMBOL);
	    label_18:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[28] = jj_gen;
	        break label_18;
	      }
	      jj_consume_token(SPACE);
	    }
	      StreamInfo si = new StreamInfo(streamName,window) ;
	          addStreams(si);
	  }

	  final public Window Window() throws ParseException {
	   Window window;
	    if (jj_2_9(2)) {
	      window = LogicalWindow();
	                            {if (true) return window;}
	    } else {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	      case TRIPLES:
	        window = PhysicalWindow();
	                               {if (true) 
	                            	   return window;}
	        break;
	      default:
	        jj_la1[29] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	    }
	    throw new Error("Missing return statement in function");
	  }

	  final public TimeUnit TimeUnit() throws ParseException {
	                     Token t; //String timeunit = "";
	    label_19:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[30] = jj_gen;
	        break label_19;
	      }
	      t = jj_consume_token(SPACE);
	                         timeunit = timeunit + t.image ;
	    }
	    t = jj_consume_token(SINGLELETTERTEXT);
	         timeunit = timeunit + t.image ;
	        if(!timeunit.equals("ms") && !timeunit.equals("s") && !timeunit.equals("m") && !timeunit.equals("h") && !timeunit.equals("d"))
	                {if (true) throw new ParseException();}
	         {if (true) 
	        	 return TimeUnit.getUnitOfMeasure(timeunit);
	         }
	    throw new Error("Missing return statement in function");
	  }

	  final public TimeIntervalDescription WindowOverlap() throws ParseException {
	                                         Token t; String tempStepValue = "";
	   TimeIntervalDescription stepDescription = new TimeIntervalDescription();
	  TimeUnit tm;
	    label_20:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[31] = jj_gen;
	        break label_20;
	      }
	      t = jj_consume_token(SPACE);
	    }
	    if (jj_2_10(2)) {
	      t = jj_consume_token(STEP);
	      label_21:
	      while (true) {
	        t = jj_consume_token(NUMBERS);
	                      tempStepValue = tempStepValue + t.image ;
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case NUMBERS:
	          ;
	          break;
	        default:
	          jj_la1[32] = jj_gen;
	          break label_21;
	        }
	      }
	      tm = TimeUnit();
	                      stepDescription.setTimeUnit(tm) ; stepDescription.setValue(Integer.parseInt(tempStepValue)); {if (true) return stepDescription;}
	    } else {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case TUMBLING:
	        t = jj_consume_token(TUMBLING);
	                       {if (true) return null;}
	        break;
	      default:
	        jj_la1[33] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	    }
	    throw new Error("Missing return statement in function");
	  }

	  final public LogicalWindow LogicalWindow() throws ParseException {
	                               Token t; String tempRangeValue = "";
	    label_22:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[34] = jj_gen;
	        break label_22;
	      }
	      jj_consume_token(SPACE);
	    }
	   TimeIntervalDescription rangeDescription = new TimeIntervalDescription();
	   TimeIntervalDescription stepDescription = new TimeIntervalDescription();
	   TimeUnit tm;
	   boolean isTumbling;
	    label_23:
	    while (true) {
	      t = jj_consume_token(NUMBERS);
	                   tempRangeValue = tempRangeValue + t.image ;
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case NUMBERS:
	        ;
	        break;
	      default:
	        jj_la1[35] = jj_gen;
	        break label_23;
	      }
	    }
	    tm = TimeUnit();
	    stepDescription = WindowOverlap();
	   rangeDescription.setTimeUnit(tm) ;
	   rangeDescription.setValue(Integer.parseInt(tempRangeValue));
	   value=Integer.parseInt(tempRangeValue);
	   rangeDescription.setTimeUnitString(timeunit);
	   if(stepDescription == null)
	                isTumbling = true;
	   else
	        isTumbling = false;

	  {if (true) return new LogicalWindow(rangeDescription,stepDescription,isTumbling) ;}
	    throw new Error("Missing return statement in function");
	  }

	  final public PhysicalWindow PhysicalWindow() throws ParseException {
	                                 Token t; String windowRange = "";
	    label_24:
	    while (true) {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SPACE:
	        ;
	        break;
	      default:
	        jj_la1[36] = jj_gen;
	        break label_24;
	      }
	      t = jj_consume_token(SPACE);
	    }
	    t = jj_consume_token(TRIPLES);
	    label_25:
	    while (true) {
	      t = jj_consume_token(NUMBERS);
	                                 windowRange = windowRange + t.image ;
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case NUMBERS:
	        ;
	        break;
	      default:
	        jj_la1[37] = jj_gen;
	        break label_25;
	      }
	    }
	    {if (true) 
	    	return new PhysicalWindow(Integer.parseInt(windowRange))  ;}
	    throw new Error("Missing return statement in function");
	  }

	  final public void WhereClause() throws ParseException {
	                       Token t;
	                       String current_word="";
	                       String previous_word="";
	                       Set<String> moleculeSet = new HashSet();
	boolean newMolecule = false;
	boolean groupOn=false;
	boolean noMolecule=false;
	int previous=0;
	String subject="";
	String object="";
	String predicate="";
	    t = jj_consume_token(WHERE);
	                sparqlQuery = sparqlQuery + t.image ;
	                sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	    if (jj_2_12(5)) {
	      label_26:
	      while (true) {
	        if (jj_2_11(5)) {
	          t = jj_consume_token(SPACE);
	                              sparqlQuery = sparqlQuery + t.image ;
	                              if(predicate.equals("rdf:type") || predicate.equals("a"))
                             	 {
	                            	  moleculeOn =false;	                            
	                            	  //System.out.println("Create Observation Molecule : "+current_word);
	                                	   object=current_word;	  
	                                	  
	                            	 // if(!KGSemanticDescription.hasMoleculeType(object))
	                                	   if(!moleculeTypeSet.contains(object))
	                                	   {
                             			 //pass sql select query to the socket to Run sql query over
                             			 //node-red and retrive the required observations
                             			 String moleculeType = object.split(":")[1];
                             			 //if(KGSemanticDescription.isEmpty())
                             			 if(moleculeTypeSet.isEmpty())
                             			 {
                             			///// sqlquery =sqlquery+" type=='"+moleculeType+"'";	                                			 
                             				where_condition=where_condition+" type=='"+moleculeType+"'";
                             				 // System.out.println("Send SQL query to NodeRed First Time..");
                             			 //System.out.println(sqlquery);
                             			 }
                             			 else
                             			 {
                                 			///// sqlquery =sqlquery+" OR type=='"+moleculeType+"'";	                                			 
                             				where_condition=where_condition+" OR type=='"+moleculeType+"'";
                             				 //System.out.println("Send Updated SQL query to NodeRed ..");
                                 			 //System.out.println(sqlquery);
                             			 }
                             			//KGSemanticDescription.updateSemanticDescription(moleculeType);
                             			moleculeTypeSet.add(object);
                             			//System.out.println("Updated Semantic Description");
//                             			try {
//                             				System.out.println("Open Websocket");
//                            			// open websocket
//                             			WebsocketSQLClientEndpoint clientEndPoint = new WebsocketSQLClientEndpoint(new URI("ws://127.0.0.1:1880/mywebsocket"));
//                         				System.out.println("Websocket is opened");
//                            			// send message to websocket
//                            			clientEndPoint.sendMessage(sqlquery+" ) ");
//                         				System.out.println("SQL Query sent to NodeRed");
//                            			// wait 5 seconds for messages from websocket
//                            			// Thread.sleep(0);
//                            		}// End of try
//                            		catch (URISyntaxException ex) {
//                            			System.err.println("URISyntaxException exception: " + ex.getMessage());
//                            		}// ENd of catch (URISyntaxException ex)
                             		 }//End of if(!KGSemanticDescription.hasMoleculeType(object))
                             		
	                                		if(!moleculeSet.contains(subject))  
	                                    	{	
	                                    		//sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+"M f:hasObs "+subject+".";
	                                    		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                    		moleculeSet.add(subject);
	                                    	}//END of if(!hasObservationIndexMap.get(previous_word))
	                                		sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+"M "+predicate+" "+object+endSymbol;
	                                		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                		endSymbol="";
	                                		predicate="";
	                                		current_word="";
                             	 }//End of if(predicate.equals("rdf:type") || predicate.equals("a"))	                              
	                              else if(predicate.equals("om-owl:procedure") || predicate.equals("om-owl:observedProperty"))
	                                  {   
	                            	  moleculeOn =false;	                            
	                            	  //System.out.println("Create Observation Molecule : "+current_word);
	                                	   object=current_word;
	                                	   
	                                		if(!moleculeSet.contains(subject))  
	                                    	{	
	                                    		//sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+"M f:hasObs "+subject+".";
	                                    		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                    		moleculeSet.add(subject);
	                                    	}//END of if(!hasObservationIndexMap.get(previous_word))
	                                		sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+"M "+predicate+" "+object+endSymbol;
	                                		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                		endSymbol="";
	                                		predicate="";
	                                		current_word="";
	                                  }//End of if(predicate.equals("om-owl:procedure") || predicate.equals("om-owl:observedProperty"))
	                                 else if(current_word.equals("rdf:type") ||current_word.equals("a") || current_word.equals("om-owl:procedure") || current_word.equals("om-owl:observedProperty"))
	                                 {	                                	 
	                                	    moleculeOn =true;	
	                                		subject=previous_word;
	                                		predicate=current_word;
	                                		previous_word="";
	                                		current_word="";
	                                		previous=0;
	                                		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                		if(!requiredVarOn)
	                                		{
	                                			 if(required_var.equals("")){
	  	                                		   required_var=" observation, procedure, type, observedProperty, result, floatValue, uom ";
	  	                                	   required_varSet.add("observation");
	  	                                	   required_varSet.add("procedure");
	  	                                	   required_varSet.add("type");
	  	                                	   required_varSet.add("observedProperty");
	  	                                	   required_varSet.add("result");
	  	                                	   required_varSet.add("floatValue");
	  	                                	   required_varSet.add("uom");
	                                			 }//End of if(required_var.equals(""))
	  	                                	   else
	  	                                	   {
	  	                                		   required_var=required_var+", observation, procedure, type, observedProperty, result, floatValue, uom ";	
	  	                                		 required_varSet.add("observation");
		  	                                	   required_varSet.add("procedure");
		  	                                	   required_varSet.add("type");
		  	                                	   required_varSet.add("observedProperty");
		  	                                	   required_varSet.add("result");
		  	                                	   required_varSet.add("floatValue");
		  	                                	   required_varSet.add("uom");
	  	                                	   }//else
	                                			 requiredVarOn=true;
	                                			 //sqlquery=" SELECT "+ required_var + " FROM OBSERVATIONSTABLE WHERE ( ";
	                                		}//End of if(!requiredVarOn)
	                                	}
	                                   else if(predicate.equals("om-owl:result")){
	                                	  //System.out.println("Create Observation Molecule :  "+current_word);
	                                  		object=current_word;
	                                  	  moleculeOn =false;	
	                                  		sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+"M "+predicate+" "+object+"M"+endSymbol;                                  		
	                                  		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                  		//sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+" "+predicate+" "+object+". ";
	                                  		endSymbol="";
	                                  		predicate="";
	                                  		current_word="";
	                                   }//End of  else if(predicate.equals("om-owl:result"))
	                                  	else if(current_word.equals("om-owl:result")){
	                                  		 moleculeOn =true;	
	                                  		subject=previous_word;
	                                  		predicate=current_word;
	                                  		previous_word="";
	                                  		current_word="";
	                                  		previous=0;
	                                  		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                  	}//End of else if(current_word.equals("om-owl:result"))
	                                   else if(predicate.equals("om-owl:floatValue") || predicate.equals("om-owl:uom")){
	                                	  //System.out.println("Create Measurement Molecule : "+current_word); 
	                                	  moleculeOn =false;		
	                                	  object=current_word;
	                                    		sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+"M "+predicate+" "+object+endSymbol;
	                                    		endSymbol="";
	                                    		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                    		predicate="";
	                                      		current_word="";
	                                   }//End if else if(predicate.equals("om-owl:floatValue") || predicate.equals("om-owl:uom"))
	                                   else if(current_word.equals("om-owl:floatValue") ||current_word.equals("om-owl:uom")){
	                                	   moleculeOn =true;		
	                                	   subject=previous_word;
	                                      		predicate=current_word;
	                                      		previous_word="";
	                                      		current_word="";
	                                      		previous=0;
	                                      		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                      	} //End of else if(current_word.equals("om-owl:floatValue") ||current_word.equals("om-owl:uom"))
	                                   else if(predicate.equals("om-owl:generatedObservation")){
	                                 	  //System.out.println("Create Measurement Molecule : "+current_word); 
	                                 	  moleculeOn =false;	
	                                 	  object=current_word;
	                                 	 if(!moleculeSet.contains(object))  
	                                 	{	
	                                 		//sparqlQueryTranslated = sparqlQueryTranslated+" "+object+"M f:hasObs "+object+".";
	                                 		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                 		moleculeSet.add(object);
	                                 	}//END of if(!hasObservationIndexMap.get(previous_word))                             		
	                                 	
	                                     		sparqlQueryTranslated = sparqlQueryTranslated+" "+subject+" "+predicate+" "+object+"M "+endSymbol;
	                                     		endSymbol="";
	                                     		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                     		predicate="";
	                                       		current_word="";
	                                    }//End if else if(predicate.equals("om-owl:floatValue") || predicate.equals("om-owl:uom"))
	                                    else if(current_word.equals("om-owl:generatedObservation")){
	                                 	   moleculeOn =true;		
	                                 	   subject=previous_word;
	                                       		predicate=current_word;
	                                       		previous_word="";
	                                       		current_word="";
	                                       		previous=0;
	                                       		sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                                       	} //End of else if(current_word.equals("om-owl:floatValue") ||current_word.equals("om-owl:uom"))
	                                  else if(current_word.isEmpty())
	                                  {
	                                	 // System.out.println("Empty : "+current_word+"hi");
	                                	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                                  }//End of  else if(current_word.isEmpty())
	                                  
	                                  
	                              else if(previous>0)
	                              {
	                            	  //noMolecule=true;
	                            	  if(!previous_word.isEmpty())
	                            	  {
	                            	  sparqlQueryTranslated = sparqlQueryTranslated + previous_word+" "+ current_word;
	                            	  sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                            	  //System.out.println("current_word is: "+current_word);
	                            	  if(!required_varSet.contains("ID") && (current_word.equals("om-owl:processLocation") || current_word.equals("om-owl:hasLocatedNearRel")))
	                            	  {
	                            		  if(required_var.equals("")){                              				 
	                                		   required_var="ID";
	                                	   }//End of if(required_var.equals(""))
	                                	   else
	                                	   {
	                                		   required_var=required_var+", "+"ID";
	                                	   }//else
                             				required_varSet.add("ID");
	                            	  }//End of  if(!required_varSet.contains("ID") && (current_word.equals("om-owl:processLocation") || current_word.equals("om-owl:hasLocatedNearRel")) 
	                            	  else{
	                            	  String[] parts = current_word.split(":");
                              			if(!required_varSet.contains(parts[1])){ 
                              				if(required_var.equals("")){                              				 
	                                		   required_var=parts[1];
	                                	   }//End of if(required_var.equals(""))
	                                	   else
	                                	   {
	                                		   required_var=required_var+", "+parts[1];
	                                	   }//else
                              				required_varSet.add(parts[1]);
                              			}//End of if(!required_varSet.contains(parts[1]))
                              			if(!required_varSet.contains("ID") && (current_word.equals("om-owl:distance") || current_word.equals("om-owl:hasLocation")))
                              			{
                              				if(required_var.equals("")){                              				 
 	                                		   required_var="ID";
 	                                	   }//End of if(required_var.equals(""))
 	                                	   else
 	                                	   {
 	                                		   required_var=required_var+", "+"ID";
 	                                	   }//else
                              				required_varSet.add("ID");
                              			}//End of 
	                            	  }//End of if(current_word.equals("om-owl:processLocation"))
//                              			String[] queryparts = sqlquery.split("FROM");
//                              			sqlquery=" SELECT "+ required_var + " FROM "+queryparts[1];
                              			//	try {
//                             				System.out.println("Open Websocket");
//                            			// open websocket
//                             			WebsocketSQLClientEndpoint clientEndPoint = new WebsocketSQLClientEndpoint(new URI("ws://127.0.0.1:1880/mywebsocket"));
//                         				System.out.println("Websocket is opened");
//                            			// send message to websocket
//                            			clientEndPoint.sendMessage(sqlquery+" ) ");
//                         				System.out.println("SQL Query sent to NodeRed");
//                         				System.out.println(sqlquery);
//                            			// wait 5 seconds for messages from websocket
//                            			// Thread.sleep(0);
//                            		}// End of try
//                            		catch (URISyntaxException ex) {
//                            			System.err.println("URISyntaxException exception: " + ex.getMessage());
//                            		}// ENd of catch (URISyntaxException ex)
                              			 
                              			 current_word="";
   	                            	  previous_word="";
   	                            	  previousOn=true;
                              		
	                            	  }//ENd of  if(!previous_word.isEmpty())
	                            	  else{
	                            	  sparqlQueryTranslated = sparqlQueryTranslated +" "+ current_word;
	                            	  sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                            	  current_word="";
	                            	  previous=0;
	                            	  previousOn=false;
	                            	  }//ENd of  else               
	                              }//End of else if(previous>0)
	                              else if(previous_word.isEmpty())
	                              {
	                              previous_word=current_word;
	                              //System.out.println("previous_word: "+previous_word);
	                              current_word="";
	                              //noMolecule=false;
	                              previous++;
	                              }//End of  else   if(previous_word.isEmpty())
	                              //sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	                               
	          label_27:
	          while (true) {
	            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	            case SINGLELETTERTEXT:
	              t = jj_consume_token(SINGLELETTERTEXT);
	              sparqlQuery = sparqlQuery + t.image ;
	              
	              if(filter_flag || groupOn){
	            	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	              }//End of if(filter_flag)
	              else{
	            	  current_word=current_word+t.image;
	              }
	              if(current_word.equalsIgnoreCase("FILTER"))
	              {
	            	  sparqlQueryTranslated = sparqlQueryTranslated + "FILTER" ;
	            	  filter_flag=true;
	        		  current_word="";
	            	 
	              }//END of if(current_word.equals("FILTER"))
	              else if(current_word.equalsIgnoreCase("OPTIONAL") || current_word.equalsIgnoreCase("UNION")){                                	  
	            	  sparqlQueryTranslated = sparqlQueryTranslated + current_word ;
	            	  //sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	            	  current_word="";
	              }//End of else if(current_word.equals("OPTIONAL"))
	              else if(current_word.equalsIgnoreCase("GROUP")){
	            	  //System.out.println("GROUP encountered : ");
	            	  sparqlQueryTranslated = sparqlQueryTranslated + current_word ;
	            	  //sparqlQueryTranslated = sparqlQueryTranslated + t.image;
	            	  groupOn=true;
	            	  current_word="";
	              }//End of else if(current_word.equals("GROUP"))
	              break;
	            case SYMBOL:
	              t = jj_consume_token(SYMBOL);
	                          sparqlQuery = sparqlQuery + t.image ;
	                          String s_image=t.image ;
	                          
	                          if(groupOn){
	                        	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                          }//End of if(groupOn)
	                          else if(filter_flag){
	                        	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                        	  if(s_image.equals("("))
	                			  {
	                		  filter_count++;
	                		  //System.out.println("filter counter++: "+filter_count);
	                			  }//End of if(s_image.equals(")")
	                	  else if(s_image.equals(")"))
	                	  {
	                		  filter_count--;
	                		 // System.out.println("filter counter--: "+filter_count);
	                	  }//End of else if(s_image.equals("("))
	                	  if(filter_count==0)
	                	  {
	                		  current_word="";
	                		  filter_flag=false;
	                	  }//End of if(filter_count==0) 
	                          }//End of if(filter_flag)
	                          else if(s_image.equals(":") || s_image.equals("?") || s_image.equals("-") || s_image.equals("_") || previousOn || s_image.equals("(") || s_image.equals(")"))
	                          {
	                        	  current_word=current_word+t.image;
	                        	  //System.out.println("current word so far: "+current_word);
	                          }
	                          else if(moleculeOn){
	                        		  endSymbol=t.image ;
	                        		  }//End of if(moleculeOn)
	                        	  else{
	                        	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                        	  //System.out.println("symbol is here: "+t.image+" and word is: "+current_word);
	                        	  current_word="";
	                        	  }
	                        	  
	                           break;
	            case NUMBERS:
	              t = jj_consume_token(NUMBERS);
	                           sparqlQuery = sparqlQuery + t.image ;
	                           if(filter_flag || groupOn){
	                         	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                           }//End of if(filter_flag)
	                           else{
	                         	  current_word=current_word+t.image;
	                           }
	              break;
	            case IRIref:
	              t = jj_consume_token(IRIref);
	                            sparqlQuery = sparqlQuery + t.image ;
	                            if(filter_flag || groupOn){
	                          	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                            }//End of if(filter_flag)
	                            else{
	                          	  current_word=current_word+t.image;
	                            }
	              break;
	            case SELECT:
	              t = jj_consume_token(SELECT);
	                          sparqlQuery = sparqlQuery + t.image ;
	                         	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                         	// System.out.println(" inside where select ");
	              break;
	            case AS:
	              t = jj_consume_token(AS);
	                      sparqlQuery = sparqlQuery + t.image ;
	                     	  sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                     
	              break;
	            case WHERE:
	              t = jj_consume_token(WHERE);
	                         sparqlQuery = sparqlQuery + t.image ;
	                          sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	                         // System.out.println(" inside where where ");
	                        
	              break;
	            default:
	              jj_la1[38] = jj_gen;
	              jj_consume_token(-1);
	              throw new ParseException();
	            }
	            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	            case SELECT:
	            case WHERE:
	            case SINGLELETTERTEXT:
	            case NUMBERS:
	            case SYMBOL:
	            case IRIref:
	            case AS:
	              ;
	              break;
	            default:
	              jj_la1[39] = jj_gen;
	              break label_27;
	            }
	          }
	        } else {
	          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	          case SPACE:
	            t = jj_consume_token(SPACE);
	                                sparqlQuery = sparqlQuery + t.image ;
	                                sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          default:
	            jj_la1[40] = jj_gen;
	            jj_consume_token(-1);
	            throw new ParseException();
	          }
	        }
	        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	        case SPACE:
	          ;
	          break;
	        default:
	          jj_la1[41] = jj_gen;
	          break label_26;
	        }
	      }
	    } else {
	      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	      case SYMBOL:
	        t = jj_consume_token(SYMBOL);
	                     sparqlQuery = sparqlQuery + t.image ;
	                     sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	        label_28:
	        while (true) {
	          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	          case SELECT:
	          case WHERE:
	          case SINGLELETTERTEXT:
	          case NUMBERS:
	          case SYMBOL:
	          case IRIref:
	          case SPACE:
	          case AS:
	            ;
	            break;
	          default:
	            jj_la1[42] = jj_gen;
	            break label_28;
	          }
	          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
	          case SINGLELETTERTEXT:
	            t = jj_consume_token(SINGLELETTERTEXT);
	                                    sparqlQuery = sparqlQuery + t.image ;
	                                    sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case SYMBOL:
	            t = jj_consume_token(SYMBOL);
	                          sparqlQuery = sparqlQuery + t.image ;
	                          sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case NUMBERS:
	            t = jj_consume_token(NUMBERS);
	                           sparqlQuery = sparqlQuery + t.image ;
	                           sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case IRIref:
	            t = jj_consume_token(IRIref);
	                            sparqlQuery = sparqlQuery + t.image ;
	                            sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case SELECT:
	            t = jj_consume_token(SELECT);
	                          sparqlQuery = sparqlQuery + t.image ;
	                          sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case AS:
	            t = jj_consume_token(AS);
	                      sparqlQuery = sparqlQuery + t.image ;
	                      sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case WHERE:
	            t = jj_consume_token(WHERE);
	                         sparqlQuery = sparqlQuery + t.image ;
	                         sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          case SPACE:
	            t = jj_consume_token(SPACE);
	                         sparqlQuery = sparqlQuery + t.image ;
	                         sparqlQueryTranslated = sparqlQueryTranslated + t.image ;
	            break;
	          default:
	            jj_la1[43] = jj_gen;
	            jj_consume_token(-1);
	            throw new ParseException();
	          }
	        }
	        break;
	      default:
	        jj_la1[44] = jj_gen;
	        jj_consume_token(-1);
	        throw new ParseException();
	      }
	    }
	    sparqlQuery = sparqlQuery + "\u005cn";
	    sparqlQueryTranslated = sparqlQueryTranslated + "\u005cn";
	  }

	  private boolean jj_2_1(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_1(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(0, xla); }
	  }

	  private boolean jj_2_2(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_2(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(1, xla); }
	  }

	  private boolean jj_2_3(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_3(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(2, xla); }
	  }

	  private boolean jj_2_4(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_4(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(3, xla); }
	  }

	  private boolean jj_2_5(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_5(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(4, xla); }
	  }

	  private boolean jj_2_6(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_6(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(5, xla); }
	  }

	  private boolean jj_2_7(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_7(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(6, xla); }
	  }

	  private boolean jj_2_8(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_8(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(7, xla); }
	  }

	  private boolean jj_2_9(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_9(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(8, xla); }
	  }

	  private boolean jj_2_10(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_10(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(9, xla); }
	  }

	  private boolean jj_2_11(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_11(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(10, xla); }
	  }

	  private boolean jj_2_12(int xla) {
	    jj_la = xla; jj_lastpos = jj_scanpos = token;
	    try { return !jj_3_12(); }
	    catch(LookaheadSuccess ls) { return true; }
	    finally { jj_save(11, xla); }
	  }

	  private boolean jj_3R_55() {
	    if (jj_scan_token(SPACE)) return true;
	    return false;
	  }

	  private boolean jj_3R_41() {
	    Token xsp;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_55()) { jj_scanpos = xsp; break; }
	    }
	    if (jj_scan_token(SINGLELETTERTEXT)) return true;
	    return false;
	  }

	  private boolean jj_3_6() {
	    if (jj_scan_token(AS)) return true;
	    return false;
	  }

	  private boolean jj_3R_38() {
	    if (jj_scan_token(NUMBERS)) return true;
	    return false;
	  }

	  private boolean jj_3_9() {
	    if (jj_3R_32()) return true;
	    return false;
	  }

	  private boolean jj_3_5() {
	    if (jj_scan_token(REGISTERSTREAM)) return true;
	    return false;
	  }

	  private boolean jj_3R_31() {
	    Token xsp;
	    xsp = jj_scanpos;
	    if (jj_3_3()) {
	    jj_scanpos = xsp;
	    if (jj_3R_38()) {
	    jj_scanpos = xsp;
	    if (jj_3R_39()) return true;
	    }
	    }
	    return false;
	  }

	  private boolean jj_3_3() {
	    if (jj_scan_token(SINGLELETTERTEXT)) return true;
	    return false;
	  }

	  private boolean jj_3R_49() {
	    if (jj_scan_token(SPACE)) return true;
	    return false;
	  }

	  private boolean jj_3R_39() {
	    if (jj_scan_token(SYMBOL)) return true;
	    return false;
	  }

	  private boolean jj_3R_48() {
	    if (jj_scan_token(WHERE)) return true;
	    return false;
	  }

	  private boolean jj_3R_47() {
	    if (jj_scan_token(AS)) return true;
	    return false;
	  }

	  private boolean jj_3R_46() {
	    if (jj_scan_token(SELECT)) return true;
	    return false;
	  }

	  private boolean jj_3R_45() {
	    if (jj_scan_token(IRIref)) return true;
	    return false;
	  }

	  private boolean jj_3R_37() {
	    if (jj_scan_token(SPACE)) return true;
	    return false;
	  }

	  private boolean jj_3R_44() {
	    if (jj_scan_token(NUMBERS)) return true;
	    return false;
	  }

	  private boolean jj_3R_43() {
	    if (jj_scan_token(SYMBOL)) return true;
	    return false;
	  }

	  private boolean jj_3_4() {
	    if (jj_scan_token(PREFIX)) return true;
	    Token xsp;
	    if (jj_3R_31()) return true;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_31()) { jj_scanpos = xsp; break; }
	    }
	    return false;
	  }

	  private boolean jj_3R_34() {
	    Token xsp;
	    xsp = jj_scanpos;
	    if (jj_3R_42()) {
	    jj_scanpos = xsp;
	    if (jj_3R_43()) {
	    jj_scanpos = xsp;
	    if (jj_3R_44()) {
	    jj_scanpos = xsp;
	    if (jj_3R_45()) {
	    jj_scanpos = xsp;
	    if (jj_3R_46()) {
	    jj_scanpos = xsp;
	    if (jj_3R_47()) {
	    jj_scanpos = xsp;
	    if (jj_3R_48()) return true;
	    }
	    }
	    }
	    }
	    }
	    }
	    return false;
	  }

	  private boolean jj_3R_42() {
	    if (jj_scan_token(SINGLELETTERTEXT)) return true;
	    return false;
	  }

	  private boolean jj_3R_30() {
	    if (jj_scan_token(FROM)) return true;
	    if (jj_scan_token(IRIref)) return true;
	    Token xsp;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_37()) { jj_scanpos = xsp; break; }
	    }
	    return false;
	  }

	  private boolean jj_3_8() {
	    if (jj_scan_token(SINGLELETTERTEXT)) return true;
	    return false;
	  }

	  private boolean jj_3_11() {
	    if (jj_scan_token(SPACE)) return true;
	    Token xsp;
	    if (jj_3R_34()) return true;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_34()) { jj_scanpos = xsp; break; }
	    }
	    return false;
	  }

	  private boolean jj_3R_35() {
	    Token xsp;
	    xsp = jj_scanpos;
	    if (jj_3_11()) {
	    jj_scanpos = xsp;
	    if (jj_3R_49()) return true;
	    }
	    return false;
	  }

	  private boolean jj_3R_54() {
	    if (jj_scan_token(AS)) return true;
	    return false;
	  }

	  private boolean jj_3R_53() {
	    if (jj_scan_token(SPACE)) return true;
	    return false;
	  }

	  private boolean jj_3_12() {
	    Token xsp;
	    if (jj_3R_35()) return true;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_35()) { jj_scanpos = xsp; break; }
	    }
	    return false;
	  }

	  private boolean jj_3R_52() {
	    if (jj_scan_token(IRIref)) return true;
	    return false;
	  }

	  private boolean jj_3_2() {
	    if (jj_3R_30()) return true;
	    return false;
	  }

	  private boolean jj_3R_51() {
	    if (jj_scan_token(NUMBERS)) return true;
	    return false;
	  }

	  private boolean jj_3R_50() {
	    if (jj_scan_token(SYMBOL)) return true;
	    return false;
	  }

	  private boolean jj_3_1() {
	    if (jj_3R_29()) return true;
	    return false;
	  }

	  private boolean jj_3_7() {
	    if (jj_scan_token(SINGLELETTERTEXT)) return true;
	    return false;
	  }

	  private boolean jj_3R_36() {
	    Token xsp;
	    xsp = jj_scanpos;
	    if (jj_3_7()) {
	    jj_scanpos = xsp;
	    if (jj_3R_50()) {
	    jj_scanpos = xsp;
	    if (jj_3R_51()) {
	    jj_scanpos = xsp;
	    if (jj_3R_52()) {
	    jj_scanpos = xsp;
	    if (jj_3R_53()) {
	    jj_scanpos = xsp;
	    if (jj_3R_54()) return true;
	    }
	    }
	    }
	    }
	    }
	    return false;
	  }

	  private boolean jj_3R_29() {
	    if (jj_scan_token(SELECT)) return true;
	    Token xsp;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_36()) { jj_scanpos = xsp; break; }
	    }
	    return false;
	  }

	  private boolean jj_3R_40() {
	    if (jj_scan_token(NUMBERS)) return true;
	    return false;
	  }

	  private boolean jj_3R_32() {
	    Token xsp;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_scan_token(18)) { jj_scanpos = xsp; break; }
	    }
	    if (jj_3R_40()) return true;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_40()) { jj_scanpos = xsp; break; }
	    }
	    if (jj_3R_41()) return true;
	    return false;
	  }

	  private boolean jj_3R_33() {
	    if (jj_scan_token(NUMBERS)) return true;
	    return false;
	  }

	  private boolean jj_3_10() {
	    if (jj_scan_token(STEP)) return true;
	    Token xsp;
	    if (jj_3R_33()) return true;
	    while (true) {
	      xsp = jj_scanpos;
	      if (jj_3R_33()) { jj_scanpos = xsp; break; }
	    }
	    return false;
	  }

	  /** Generated Token Manager. */
	  public CsparqlParserTokenManager token_source;
	  SimpleCharStream jj_input_stream;
	  /** Current token. */
	  public Token token;
	  /** Next token. */
	  public Token jj_nt;
	  private int jj_ntk;
	  private Token jj_scanpos, jj_lastpos;
	  private int jj_la;
	  private int jj_gen;
	  final private int[] jj_la1 = new int[45];
	  static private int[] jj_la1_0;
	  static {
	      jj_la1_init_0();
	   }
	   private static void jj_la1_init_0() {
	      jj_la1_0 = new int[] {0x60,0xd00,0x1000,0x1000,0x2000,0x18000,0x1c000,0x40000,0x40,0x40000,0x100000,0xc000,0xc000,0x40000,0x1c000,0x41c000,0x41c000,0x41ca80,0x41ca80,0x41ca80,0x40000,0x47c000,0x478000,0x7c000,0x78000,0x40000,0x800000,0x40000,0x40000,0x8040000,0x40000,0x40000,0x8000,0x4000000,0x40000,0x8000,0x40000,0x8000,0x43e080,0x43e080,0x40000,0x40000,0x47e080,0x47e080,0x10000,};
	   }
	  final private JJCalls[] jj_2_rtns = new JJCalls[12];
	  private boolean jj_rescan = false;
	  private int jj_gc = 0;

	  /** Constructor with InputStream. */
	  public FactorizedCsparqlParser(java.io.InputStream stream) {
	     this(stream, null);
	  }
	  /** Constructor with InputStream and supplied encoding */
	  public FactorizedCsparqlParser(java.io.InputStream stream, String encoding) {
	    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	    token_source = new CsparqlParserTokenManager(jj_input_stream);
	    token = new Token();
	    jj_ntk = -1;
	    jj_gen = 0;
	    for (int i = 0; i < 45; i++) jj_la1[i] = -1;
	    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	  }

	  /** Reinitialise. */
	  public void ReInit(java.io.InputStream stream) {
	     ReInit(stream, null);
	  }
	  /** Reinitialise. */
	  public void ReInit(java.io.InputStream stream, String encoding) {
	    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	    token_source.ReInit(jj_input_stream);
	    token = new Token();
	    jj_ntk = -1;
	    jj_gen = 0;
	    for (int i = 0; i < 45; i++) jj_la1[i] = -1;
	    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	  }

	  /** Constructor. */
	  public FactorizedCsparqlParser(java.io.Reader stream) {
	    jj_input_stream = new SimpleCharStream(stream, 1, 1);
	    token_source = new CsparqlParserTokenManager(jj_input_stream);
	    token = new Token();
	    jj_ntk = -1;
	    jj_gen = 0;
	    for (int i = 0; i < 45; i++) jj_la1[i] = -1;
	    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	  }

	  /** Reinitialise. */
	  public void ReInit(java.io.Reader stream) {
	    jj_input_stream.ReInit(stream, 1, 1);
	    token_source.ReInit(jj_input_stream);
	    token = new Token();
	    jj_ntk = -1;
	    jj_gen = 0;
	    for (int i = 0; i < 45; i++) jj_la1[i] = -1;
	    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	  }

	  /** Constructor with generated Token Manager. */
	  public FactorizedCsparqlParser(CsparqlParserTokenManager tm) {
	    token_source = tm;
	    token = new Token();
	    jj_ntk = -1;
	    jj_gen = 0;
	    for (int i = 0; i < 45; i++) jj_la1[i] = -1;
	    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	  }

	  /** Reinitialise. */
	  public void ReInit(CsparqlParserTokenManager tm) {
	    token_source = tm;
	    token = new Token();
	    jj_ntk = -1;
	    jj_gen = 0;
	    for (int i = 0; i < 45; i++) jj_la1[i] = -1;
	    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	  }

	  private Token jj_consume_token(int kind) throws ParseException {
	    Token oldToken;
	    if ((oldToken = token).next != null) token = token.next;
	    else token = token.next = token_source.getNextToken();
	    jj_ntk = -1;
	    if (token.kind == kind) {
	      jj_gen++;
	      if (++jj_gc > 100) {
	        jj_gc = 0;
	        for (int i = 0; i < jj_2_rtns.length; i++) {
	          JJCalls c = jj_2_rtns[i];
	          while (c != null) {
	            if (c.gen < jj_gen) c.first = null;
	            c = c.next;
	          }
	        }
	      }
	      return token;
	    }
	    token = oldToken;
	    jj_kind = kind;
	    throw generateParseException();
	  }

	static private final class LookaheadSuccess extends java.lang.Error { }
	  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
	  private boolean jj_scan_token(int kind) {
	    if (jj_scanpos == jj_lastpos) {
	      jj_la--;
	      if (jj_scanpos.next == null) {
	        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	      } else {
	        jj_lastpos = jj_scanpos = jj_scanpos.next;
	      }
	    } else {
	      jj_scanpos = jj_scanpos.next;
	    }
	    if (jj_rescan) {
	      int i = 0; Token tok = token;
	      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	      if (tok != null) jj_add_error_token(kind, i);
	    }
	    if (jj_scanpos.kind != kind) return true;
	    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	    return false;
	  }


	/** Get the next Token. */
	  final public Token getNextToken() {
	    if (token.next != null) token = token.next;
	    else token = token.next = token_source.getNextToken();
	    jj_ntk = -1;
	    jj_gen++;
	    return token;
	  }

	/** Get the specific Token. */
	  final public Token getToken(int index) {
	    Token t = token;
	    for (int i = 0; i < index; i++) {
	      if (t.next != null) t = t.next;
	      else t = t.next = token_source.getNextToken();
	    }
	    return t;
	  }

	  private int jj_ntk() {
	    if ((jj_nt=token.next) == null)
	      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	    else
	      return (jj_ntk = jj_nt.kind);
	  }

	  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
	  private int[] jj_expentry;
	  private int jj_kind = -1;
	  private int[] jj_lasttokens = new int[100];
	  private int jj_endpos;

	  private void jj_add_error_token(int kind, int pos) {
	    if (pos >= 100) return;
	    if (pos == jj_endpos + 1) {
	      jj_lasttokens[jj_endpos++] = kind;
	    } else if (jj_endpos != 0) {
	      jj_expentry = new int[jj_endpos];
	      for (int i = 0; i < jj_endpos; i++) {
	        jj_expentry[i] = jj_lasttokens[i];
	      }
	      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
	        int[] oldentry = (int[])(it.next());
	        if (oldentry.length == jj_expentry.length) {
	          for (int i = 0; i < jj_expentry.length; i++) {
	            if (oldentry[i] != jj_expentry[i]) {
	              continue jj_entries_loop;
	            }
	          }
	          jj_expentries.add(jj_expentry);
	          break jj_entries_loop;
	        }
	      }
	      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	    }
	  }

	  /** Generate ParseException. */
	  public ParseException generateParseException() {
	    jj_expentries.clear();
	    boolean[] la1tokens = new boolean[28];
	    if (jj_kind >= 0) {
	      la1tokens[jj_kind] = true;
	      jj_kind = -1;
	    }
	    for (int i = 0; i < 45; i++) {
	      if (jj_la1[i] == jj_gen) {
	        for (int j = 0; j < 32; j++) {
	          if ((jj_la1_0[i] & (1<<j)) != 0) {
	            la1tokens[j] = true;
	          }
	        }
	      }
	    }
	    for (int i = 0; i < 28; i++) {
	      if (la1tokens[i]) {
	        jj_expentry = new int[1];
	        jj_expentry[0] = i;
	        jj_expentries.add(jj_expentry);
	      }
	    }
	    jj_endpos = 0;
	    jj_rescan_token();
	    jj_add_error_token(0, 0);
	    int[][] exptokseq = new int[jj_expentries.size()][];
	    for (int i = 0; i < jj_expentries.size(); i++) {
	      exptokseq[i] = jj_expentries.get(i);
	    }
	    return new ParseException(token, exptokseq, tokenImage);
	  }

	  /** Enable tracing. */
	  final public void enable_tracing() {
	  }

	  /** Disable tracing. */
	  final public void disable_tracing() {
	  }

	  private void jj_rescan_token() {
	    jj_rescan = true;
	    for (int i = 0; i < 12; i++) {
	    try {
	      JJCalls p = jj_2_rtns[i];
	      do {
	        if (p.gen > jj_gen) {
	          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
	          switch (i) {
	            case 0: jj_3_1(); break;
	            case 1: jj_3_2(); break;
	            case 2: jj_3_3(); break;
	            case 3: jj_3_4(); break;
	            case 4: jj_3_5(); break;
	            case 5: jj_3_6(); break;
	            case 6: jj_3_7(); break;
	            case 7: jj_3_8(); break;
	            case 8: jj_3_9(); break;
	            case 9: jj_3_10(); break;
	            case 10: jj_3_11(); break;
	            case 11: jj_3_12(); break;
	          }
	        }
	        p = p.next;
	      } while (p != null);
	      } catch(LookaheadSuccess ls) { }
	    }
	    jj_rescan = false;
	  }

	  private void jj_save(int index, int xla) {
	    JJCalls p = jj_2_rtns[index];
	    while (p.gen > jj_gen) {
	      if (p.next == null) { p = p.next = new JJCalls(); break; }
	      p = p.next;
	    }
	    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
	  }

	  static final class JJCalls {
	    int gen;
	    Token first;
	    int arg;
	    JJCalls next;
	  }

}
