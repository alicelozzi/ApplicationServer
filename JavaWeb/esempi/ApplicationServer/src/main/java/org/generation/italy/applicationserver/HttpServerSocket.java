package org.generation.italy.applicationserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.generation.italy.applicationserver.control.BancaControlException;
import org.generation.italy.applicationserver.control.CalcolatriceControlException;
import org.generation.italy.applicationserver.control.ClienteServlet;
import org.generation.italy.applicationserver.control.UtenteAnonimoServlet;

/**
 *
 * @author Angelo Pasquarelli
 */

// Read the full article https://dev.to/mateuszjarzyna/build-your-own-http-server-in-java-in-less-than-one-hour-only-get-method-2k02
public class HttpServerSocket {

    /*
        AVVIA IL SOCKET SERVER SULLA PORTA PREDEFINITA 8081
     */
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {              //socket server impostato per ascoltare sulla porta 8081
            System.out.println("server socket avviato!");        	
            while (true) {
                try (Socket httpClientRequest = serverSocket.accept()) {                   //in ascolto sulla porta 8081
                    if (httpClientRequest != null) {
                        handleHttpRequest(httpClientRequest);                              //gestione dei pacchetti informativi arrivati dal client
                    }
                } catch (IOException oEx) {
                    System.out.println("Errore nella lettura del messaggio di richiesta in arrivo dallo stream del socket!");        	
                }
            }
        }
        catch (IOException ioException) {
            System.out.println("Impossibile avviare l'application server. Verificare che la porta non sia gi� occupata!");        	
        }
    }

    /*
        GESTISCE LE RICHIESTE (HTTP REQUEST) IN ARRIVO DAL CLIENT (WEB BROWSER) 
     */
    private static void handleHttpRequest(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String headerLine;
        while ((headerLine = br.readLine()).length() != 0) {
            requestBuilder.append(headerLine.trim() + "\r\n");
        }

        String request = requestBuilder.toString();
        System.out.println("Messaggio HTTP  REQUEST dal client: \n" + request);

        // ANALIZZA PRIMA RIGA DELLA REQUEST INVIATA DAL CLIENT ({METHOD} {URI} {PROTOCOL VERSION}) 
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String uri = requestLine[1];
        String protocolVersion = requestLine[2];

        //ANALIZZA GLI HEADEAR PRESENTI DALLA TERZA RIGA IN POI DELLA REQUEST
        String host = requestsLines[1].split(" ")[1];                           //prima riga header: estrae valore header -Host-

        List<String> headers = new ArrayList<>();
        for (int i = 2; i < requestsLines.length; i++) {
            String header = requestsLines[i];                                   //riga i-esima header: memorizza chiave e valore header
            headers.add(header);
        }

        //PREPARA UN MESSAGGIO DI LOG DESCRIVENTE LA RICHIESTA PERVENUTA (HTTP-REQUEST)
        String accessLog
                = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                        client.toString(),
                         method,
                         uri,
                         protocolVersion,
                         host,
                         headers.toString());
        System.out.println(accessLog);

        String[] uriResourceQueryString = uri.split("\\?");                     //separa nome risorsa da eventuale querystring

        String[] resourcePath = uriResourceQueryString[0].split("\\/");         //separa il path della risorsa estraendo le info su: nome webapp, nome servlet, nome azione

        String webAppName = "";                                                 //nome della webapp a cui è relativa la richiesta
        String servletName = "";                                                //nome del controller applicativo per cui si richiede l'operazione
        String actionName = "";                                                 //nome dell'operazione richiesta

        if (resourcePath.length == 4) {
            webAppName = resourcePath[1];                                       //nome della webapp a cui è relativa la richiesta
            servletName = resourcePath[2];                                      //nome del controller applicativo per cui si richiede l'operazione
            actionName = resourcePath[3];                                       //nome dell'operazione richiesta
        }
        
        //String[] resource = uri.split("\\/");                                 //separa nome risorsa da eventuale querystring

        
        //String resourceName = uriResourceQueryString[0];                      //nome della risorsa da inviare al client

        String webAppServiceName = "";                                          //nome della web app a cui fare riferimento
        byte[] htmlContentPage = "".getBytes();                                 //contenuto della pagina html risutante dall'elaborazione della richiesta

        Map<String, String> parameterValueCollection = new HashMap<String, String>();
                                                                                //collection di parametri passati inin get o post del messaggio http request
        switch (method.toUpperCase()) {

            //****** INIZIO GESTIONE DELLA RICHIESTA INTERPRETANDOLA COME RICHIESTA CON METODO 'GET' *********
            case "GET":

                //********** GESTIONE EVENTUALE QUERYSTRING CON ATTIVAZIONE SERVIZIO APPLICATIVO **********
                //Map<String, String> queryStringParameterValueCollection = new HashMap<String, String>();
                //valori querystring in HashMap collection
                if (uriResourceQueryString.length > 1) {
                    String[] queryStringParameterValueArray = uriResourceQueryString[1].split("&");
                    for (int i = 0; i < queryStringParameterValueArray.length; i++) {
                        String parameter = queryStringParameterValueArray[i].split("=")[0];
                        String value = queryStringParameterValueArray[i].split("=")[1];
                        parameterValueCollection.put(parameter.toLowerCase(), value.toLowerCase());
                    }

                    //webAppServiceName = queryStringParameterValueCollection.get("servizio-app");
                    

                }

                break;

            //------- FINE GESTIONE DELLA RICHIESTA INTERPRETANDOLA COME RICHIESTA CON METODO 'GET' --------
            //****** INIZIO GESTIONE DELLA RICHIESTA INTERPRETANDOLA COME RICHIESTA CON METODO 'POST' *********
            case "POST":
                //RECUPERA LA LINEA CON I DATI DEL FORM HTML (la parte {body} del messaggio HTTP REQUEST)
                StringBuilder postDataLine = new StringBuilder();
                while (br.ready()) {
                    postDataLine.append((char) br.read());
                }
                System.out.println(postDataLine.toString());

                //HashMap<String, String> postDataParameterValueCollection = new HashMap<String, String>();
                String[] postDataArray = postDataLine.toString().split("&");

                for (int i = 0; i < postDataArray.length; i++) {
                    String parameter = postDataArray[i].split("=")[0];
                    String value = postDataArray[i].split("=")[1];
                    parameterValueCollection.put(parameter.toLowerCase(), value.toLowerCase());
                }

                //webAppServiceName = postDataParameterValueCollection.get("servizio-app");

                //htmlContentPage = webAppServiceManager(webAppName, servletName, actionName, postDataParameterValueCollection);

                break;

            //------- FINE GESTIONE DELLA RICHIESTA INTERPRETANDOLA COME RICHIESTA CON METODO 'POST' --------
        }

        htmlContentPage = webAppServiceManager(webAppName, servletName, actionName, parameterValueCollection);
                                                                                //richiama la servlet della webapp per un controller con riotrno del contenuto html per il browser
        
        if (htmlContentPage.length == 0) {
            byte[] notFoundContent = new String("<HTML><HEAD></HEAD><BODY><h1>File Not Found :(</h1></BODY>").getBytes();
            sendHttpResponse(client, "404", "Not Found", "text/html", notFoundContent);
        }
        else {
            sendHttpResponse(client, "200", "OK", "text/html", htmlContentPage);
        }
        
    }

    /*
        INVIA LA RISPOSTA AL CLIENT (HTTP RESPONSE)
     */
    private static void sendHttpResponse(Socket client, String statusCode, String statusReason, String contentType, byte[] content) throws IOException {
        try {
            OutputStream clientOutput = client.getOutputStream();
            
            //RIGA 0 DELLA RISPOSTA
            clientOutput.write(new String("HTTP/1.1 " + statusCode + " " + statusReason).getBytes());              //{PROTOCOL VERSION} {STATUS} {REASON}
            clientOutput.write("\r\n".getBytes());

            //RIGA 1 DELLA RISPOSTA: non esiste nella specifica HTTP 
            //clientOutput.write("\r\n".getBytes());

            //RIGA 2 DELLA RISPOSTA
            clientOutput.write(new String("ContentType: " + contentType).getBytes());//{Content-type} header, tipo di conteuto della risposta 
            clientOutput.write("\r\n".getBytes());

            //RIGA 3 DELLA RISPOSTA
            clientOutput.write("\r\n".getBytes());

            //RIGA 4 DELLA RISPOSTA
            clientOutput.write(content);                                            //{body}, contenuto della risposta 
            clientOutput.write("\r\n".getBytes());

            clientOutput.write("\r\n".getBytes());
            clientOutput.flush();
            client.close();
        }
        catch (Exception ex) {
            System.out.println("Errore socket in output: " + ex.getMessage());
        }
    }



    public static byte[] webAppServiceManager(String webAppName
                                            , String servletName
                                            , String actionName
                                            , Map<String, String> parameterValueCollection) {

        byte[] htmlContentPage = "".getBytes();                                 //contenuto html, risultato dell'esecuzione dell'azione richiesta per una determinata webapp

        
        switch (webAppName.toLowerCase()) {
        
            case "calcolatrice":
                
                switch (servletName.toLowerCase()) {
                    
                    case "utenteanonimo":

						try {
							htmlContentPage = UtenteAnonimoServlet.executeAction(actionName, parameterValueCollection);
						} catch (CalcolatriceControlException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}

                        break;

                }
                
                break;
                
            case "banca":
            	
            	switch (servletName.toLowerCase()) {
            	
            		case "cliente":
						try {
							htmlContentPage = ClienteServlet.executeAction(actionName, parameterValueCollection);
						} catch (BancaControlException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}

                        break;
                        
            		case "operatore-banca":
//						try {
//							htmlContentPage = OperatoreBancaServlet.executeAction(actionName, parameterValueCollection);
//						} catch (BancaControlException e) {
//							// TODO Auto-generated catch block
//							System.out.println(e.getMessage());
//						}

            			break;
            	}
            	
            	break;

            default:;

    	}
                
        return htmlContentPage;
    }
}

