package org.txtana.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.txtana.crawl.Crawler;
import org.txtana.util.CrawlerException;
import org.txtana.rank.Ranker;
import org.txtana.vo.RankResponse;
import org.txtana.vo.WebPageRequest;
import org.txtana.vo.WordStats;


@Path("/webPageAnalysis")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TextAnalyticsResource {

    static {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.WARNING);
    }

    @POST
    public Response analyzeText(WebPageRequest webReq) {
        Response response = null;
        try {
            String txt = Crawler.crawlWebPage(webReq.getWeblink());

            String[] ar = txt.split(" ");
            List<String> arList = Arrays.asList(ar);
            System.out.println("\nTop Words: ");
            List<WordStats> topWords = Ranker.mostFrequentWords(arList,Integer.parseInt(webReq.getCount()));

            System.out.println("\nTop Word Pairs: ");
            List<WordStats> topWordPairs = Ranker.mostFrequentWords(
                    createWordPairs(arList),Integer.parseInt(webReq.getCount()));

            RankResponse resp = new RankResponse(topWords, topWordPairs);

            response =  Response.ok(resp).build();

        } catch (CrawlerException e) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;

    }

    private static List<String> createWordPairs(List<String> words) {
        List<String> pairs = new ArrayList<String>();
        for (int i = 0; i < words.size()-1; ++i) {
            pairs.add(words.get(i) + " " + words.get(i+1));
        }
        return pairs;
    }
}