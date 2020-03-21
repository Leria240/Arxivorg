package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiveTest {

    @Test
    public void testAddArticles() {
        Archive archive = new Archive();
        archive.addArticles();

        assertNotNull(archive.getArticles(), "L'article est vide");

        List<String> authorlist = new ArrayList<>();
        authorlist.add("Thomas Bachlechner");
        authorlist.add("Bodhisattwa Prasad Majumder");
        authorlist.add("Huanru Henry Mao");
        authorlist.add("Garrison W. Cottrell");
        authorlist.add("Julian McAuley");
        Authors authors = new Authors(authorlist);

        List<String> categorylist = new ArrayList<>();
        categorylist.add("cs.LG");
        categorylist.add("cs.CL");
        categorylist.add("stat.ML");


        Article article1 = new Article("http://arxiv.org/abs/2003.04887v1",
                "2020-03-10T17:58:01Z","2020-03-10T17:58:01Z",
                "ReZero is All You Need: Fast Convergence at Large Depth",
                "  Deep networks have enabled significant performance gains across domains, but\n" +
                        "            they often suffer from vanishing/exploding gradients. This is especially true\n" +
                        "            for Transformer architectures where depth beyond 12 layers is difficult to\n" +
                        "            train without large datasets and computational budgets. In general, we find\n" +
                        "            that inefficient signal propagation impedes learning in deep networks. In\n" +
                        "            Transformers, multi-head self-attention is the main cause of this poor signal\n" +
                        "            propagation. To facilitate deep signal propagation, we propose ReZero, a simple\n" +
                        "            change to the architecture that initializes an arbitrary layer as the identity\n" +
                        "            map, using a single additional learned parameter per layer. We apply this\n" +
                        "            technique to language modeling and find that we can easily train\n" +
                        "            ReZero-Transformer networks over a hundred layers. When applied to 12 layer\n" +
                        "            Transformers, ReZero converges 56% faster on enwiki8. ReZero applies beyond\n" +
                        "            Transformers to other residual networks, enabling 1,500% faster convergence for\n" +
                        "            deep fully connected networks and 32% faster convergence for a ResNet-56\n" +
                        "            trained on CIFAR 10.\n" +
                        "        ",
                authors,
                "http://arxiv.org/abs/2003.04887v1",
                "http://arxiv.org/pdf/2003.04887v1",
                categorylist);

        assertEquals(archive.getArticles().get(0).getId(), article1.getId());
        assertEquals(archive.getArticles().get(0).getUpdated(), article1.getUpdated());
        assertEquals(archive.getArticles().get(0).getPublished(), article1.getPublished());
        assertEquals(archive.getArticles().get(0).getTitle(),article1.getTitle());
        assertEquals(archive.getArticles().get(0).getSummary(), article1.getSummary());
        assertEquals(archive.getArticles().get(0).getAuthors().getData(),article1.getAuthors().getData());
        assertEquals(archive.getArticles().get(0).getURL_pageArxiv(), article1.getURL_pageArxiv());
        assertEquals(archive.getArticles().get(0).getURL_PDF(), article1.getURL_PDF());
        assertEquals(archive.getArticles().get(0).getCategory(),article1.getCategory());


        List<String> authorlist2 = new ArrayList<>();
        authorlist2.add("Piji Li");
        Authors authors2 = new Authors(authorlist2);

        List<String> categorylist2 = new ArrayList<>();
        categorylist2.add("cs.CL");
        categorylist2.add("cs.AI");


        Article article10 = new Article("http://arxiv.org/abs/2003.04195v1",
                "2020-03-09T15:20:21Z",
                "2020-03-09T15:20:21Z",
                "An Empirical Investigation of Pre-Trained Transformer Language Models\n" +
                        "            for Open-Domain Dialogue Generation",
                "  We present an empirical investigation of pre-trained Transformer-based\n" +
                        "            auto-regressive language models for the task of open-domain dialogue\n" +
                        "            generation. Training paradigm of pre-training and fine-tuning is employed to\n" +
                        "            conduct the parameter learning. Corpora of News and Wikipedia in Chinese and\n" +
                        "            English are collected for the pre-training stage respectively. Dialogue context\n" +
                        "            and response are concatenated into a single sequence utilized as the input of\n" +
                        "            the models during the fine-tuning stage. A weighted joint prediction paradigm\n" +
                        "            for both context and response is designed to evaluate the performance of models\n" +
                        "            with or without the loss term for context prediction. Various of decoding\n" +
                        "            strategies such as greedy search, beam search, top-k sampling, etc. are\n" +
                        "            employed to conduct the response text generation. Extensive experiments are\n" +
                        "            conducted on the typical single-turn and multi-turn dialogue corpora such as\n" +
                        "            Weibo, Douban, Reddit, DailyDialog, and Persona-Chat. Detailed numbers of\n" +
                        "            automatic evaluation metrics on relevance and diversity of the generated\n" +
                        "            results for the languages models as well as the baseline approaches are\n" +
                        "            reported.\n" +
                        "        ",
                authors2,
                "http://arxiv.org/abs/2003.04195v1",
                "http://arxiv.org/pdf/2003.04195v1",
                categorylist2);

        assertEquals(archive.getArticles().get(9).getId(), article10.getId());
        assertEquals(archive.getArticles().get(9).getUpdated(), article10.getUpdated());
        assertEquals(archive.getArticles().get(9).getPublished(), article10.getPublished());
        assertEquals(archive.getArticles().get(9).getTitle(),article10.getTitle());
        assertEquals(archive.getArticles().get(9).getSummary(), article10.getSummary());
        assertEquals(archive.getArticles().get(9).getAuthors().getData(),article10.getAuthors().getData());
        assertEquals(archive.getArticles().get(9).getURL_pageArxiv(), article10.getURL_pageArxiv());
        assertEquals(archive.getArticles().get(9).getURL_PDF(), article10.getURL_PDF());
        assertEquals(archive.getArticles().get(9).getCategory(),article10.getCategory());

    }

}