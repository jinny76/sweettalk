package com.jbolt.sweettalk.message.qq;

import com.jbolt.sweettalk.domain.qq.Article;

import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ArticlePagingMsg extends PagingMsg<Article> {

    List<Article> info;

    public List<Article> getInfo() {
        return info;
    }

    public void setInfo(List<Article> info) {
    }
}
