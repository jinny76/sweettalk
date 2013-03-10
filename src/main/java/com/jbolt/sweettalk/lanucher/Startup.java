package com.jbolt.sweettalk.lanucher;

import com.jbolt.sweettalk.domain.IArticle;
import com.jbolt.sweettalk.impl.QQArticleManagerImpl;
import com.jbolt.sweettalk.interfaces.ArticleManager;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.MultiException;

import java.io.IOException;
import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class Startup {

    public static void main(String[] args) {
        try {
            Server jetty = new Server();
            jetty.addListener("7777");
            WebApplicationContext webAppContext =
                jetty.addWebApplication("/", "W:\\Jinni\\Project\\sweettalk\\webroot");
            jetty.start();
        } catch (MultiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArticleManager articleManager = QQArticleManagerImpl.getInstance();
        articleManager.doAuth();
        List<IArticle> articles = articleManager.getHomeTimeline();
        for (IArticle article : articles) {
            System.out.println(article.getNick() + " = " + article.getText());
        }
    }
}
