package org.txtana.crawl;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.txtana.util.CrawlerException;

public class Crawler {

    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static String crawlWebPage(String uri) throws CrawlerException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();

        HttpResponse<String> response = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(response.body())));
            NodeList nodes = document.getChildNodes();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < nodes.getLength(); i++) {
                extractTextFromNode(nodes.item(i),sb);
            }
            System.out.println(sb);
            return sb.toString();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Exception occured while reading content from input source.",e);
            throw new CrawlerException(e);
            //Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(report).type(ContentType.TEXT_XML_UTF_8).build();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE,"Exception occured while reading content from input source.",e);
            throw new CrawlerException(e);
        } catch (SAXException e) {
            logger.log(Level.SEVERE,"Exception occured while parsing web page content as the html tags are not well formed.",e);
            throw new CrawlerException(e);
        } catch (ParserConfigurationException e) {
            logger.log(Level.SEVERE,"Exception occured while parsing web page content.",e);
            throw new CrawlerException(e);
        }
    }

    private static void extractTextFromNode(Node text, StringBuilder sb) {
        NodeList textElems = text.getChildNodes();
        for (int i = 0; i < textElems.getLength(); i++) {
            Node child = textElems.item(i);
            if(child.getNodeType() == Node.TEXT_NODE) {
                String str = child.getNodeValue();
                if(str.isBlank()) continue;//skip if the value is empty/whitespace

                //replace new line characters, multiple spaces and apostrophe s with a single space
                String regex = "\\s+|'s";
                //Compiling the regular expression
                Pattern pattern = Pattern.compile(regex);
                //Retrieving the matcher object
                Matcher matcher = pattern.matcher(str);
                //Replacing all space characters with single space
                str = matcher.replaceAll(" ");
                str = str.trim();//remove leading and trailing whitespaces
        
                sb.append(str + " ");
            }

            extractTextFromNode(child,sb);
        }
    }
}