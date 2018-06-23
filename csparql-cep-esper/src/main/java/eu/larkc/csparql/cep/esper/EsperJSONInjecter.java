/**
 * @Author- Farah Karim
 */
package eu.larkc.csparql.cep.esper;

import java.util.concurrent.ArrayBlockingQueue;

//import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.JSONData;

public class EsperJSONInjecter implements Runnable {

	//private ArrayBlockingQueue<RdfQuadruple> queue;
	//private EsperEngine engine;
	//private RdfQuadruple tempQuadruple;
	private ArrayBlockingQueue<JSONData> queue;
	private EsperEngineForJSON engine;
	private JSONData tempQuadruple;


	private long lastQuadrupleTS = 0;
	private long TSDiff = 0;
	private long controlTSDiff = 0;
	private boolean firstElement = true;

	//public EsperInjecter(ArrayBlockingQueue<RdfQuadruple> queue, EsperEngine engine) {
	//	super();
	//	this.queue = queue;
	//	this.engine = engine;
	//}

	

public EsperJSONInjecter(ArrayBlockingQueue<JSONData> queue, EsperEngineForJSON engine) {
		super();
		this.queue = queue;
		this.engine = engine;
	}


	public void run() {
		while(true){
			synchronized(queue){
				tempQuadruple = queue.poll();
			}
			if(tempQuadruple != null){
				//La prima quadrupla deve far riferimento al tempo attuale di sistema per capire quando essere iniettata
				if(firstElement){
					controlTSDiff = tempQuadruple.getTimestamp() - System.currentTimeMillis();
					if(controlTSDiff <= 0){
						//printQuadruple(tempQuadruple);
						engine.getEpService().getEPRuntime().sendEvent(tempQuadruple);
						lastQuadrupleTS = tempQuadruple.getTimestamp();
					} else {
						try {
							Thread.sleep(controlTSDiff);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//printQuadruple(tempQuadruple);
						engine.getEpService().getEPRuntime().sendEvent(tempQuadruple);
						lastQuadrupleTS = tempQuadruple.getTimestamp();
					}
					firstElement = false;
				} else {
					
					/*Dalla seconda quadrupla in il sistema deve capire se la sleep tra una quadrupla e l'altra ??? stata gi??? effettuata a monte per evitare di farla
					 * due volte. 
					 * Controllo se il TS della quadrupla attuale coincide, o ??? di poco precedente, al tempo attuale di sistema.
					 * Se risulta in linea con il tempo attuale di sistema la inserisco subito, altrimenti faccio il controllo con la quadrupla precendente e inserisco
					 * l'eventuale tempo di attesa
					*/
					if(tempQuadruple.getTimestamp() - System.currentTimeMillis() <= 0){
						//printQuadruple(tempQuadruple);
						engine.getEpService().getEPRuntime().sendEvent(tempQuadruple);
						lastQuadrupleTS = tempQuadruple.getTimestamp();
					} else {

						TSDiff = tempQuadruple.getTimestamp() - lastQuadrupleTS;

						if(TSDiff < 0){
							System.out.println();
							System.out.println("Quadruple Timestamp ERROR , " + tempQuadruple.toString());
							System.out.println();
						} else if(TSDiff > 0){
							try {
								Thread.sleep(TSDiff);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//printQuadruple(tempQuadruple);
							engine.getEpService().getEPRuntime().sendEvent(tempQuadruple);
						} else{
							//printQuadruple(tempQuadruple);
							engine.getEpService().getEPRuntime().sendEvent(tempQuadruple);
						}
						lastQuadrupleTS = tempQuadruple.getTimestamp();
					}
				}
			}
		}
	}

	private void printQuadruple(JSONData quad){
//		System.out.println(quad + " , " + System.currentTimeMillis());
	}
}
