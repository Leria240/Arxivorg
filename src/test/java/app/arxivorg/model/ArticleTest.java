package app.arxivorg.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    static String title1 = "ReZero is All You Need: Fast Convergence at Large Depth";
    static String title2 = "An Empirical Investigation of Pre-Trained Transformer Language Models for Open-Domain Dialogue Generation";
    static String id1 = "http://arxiv.org/abs/2003.04887v1";
    static String id2 = "http://arxiv.org/abs/2003.04195v1";
    static String updated1 = "2020-03-10T17:58:01Z";
    static String updated2 = "2020-03-09T15:20:21Z";
    static String published1 = "2020-03-10T17:58:01Z";
    static String published2 = "2020-03-09T15:20:21Z";
    static String summary1 = "  Deep networks have enabled significant performance gains across domains, but\n" +
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
            "        ";
    static String summary2 = "  We present an empirical investigation of pre-trained Transformer-based\n" +
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
            "        ";

    static URL page1;
    static URL PDF1;
    static URL page2;
    static URL PDF2;

    static {
        try {
            page1 = new URL("http://arxiv.org/abs/2003.04887v1");
            PDF1 = new URL("http://arxiv.org/pdf/2003.04887v1");
            page2 = new URL("http://arxiv.org/abs/2003.04195v1");
            PDF2 = new URL("http://arxiv.org/pdf/2003.04195v1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static Authors authors1 = new Authors( Arrays.asList("Thomas Bachlechner","Bodhisattwa Prasad Majumder","Huanru Henry Mao", "Garrison W. Cottrell", "Julian McAuley"));
    static Authors authors2 = new Authors(Collections.singletonList("Piji Li"));
    static List<String> category1 = Arrays.asList("cs.LG","cs.CL","stat.ML");
    static List<String> category2 = Arrays.asList("cs.CL","cs.AI");
    static Article article1 = new Article(id1,updated1,published1,title1,summary1,authors1,page1,PDF1,category1);
    static Article article10 = new Article(id2,updated2,published2,title2,summary2,authors2,page2,PDF2,category2);


    @Test
    public void testToString(){
        Archive archive = new Archive();
        archive.addArticles(new File("atomFile1.xml"));

        String expected = "Title: " + title1 + "\n\t" +
                "Authors: " + authors1.toString() + "\n\n" +
                "Summary: " + summary1 + "\n\n" +
                "un lien vers la page ArXiv: " + page1;

        assertEquals(expected,archive.getArticles().get(0).toString());
    }

    @Test
    public void testIsFavoriteItem(){
        assertFalse(article1.isFavoriteItem());
        assertFalse(article10.isFavoriteItem());
    }



}