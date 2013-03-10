package com.jbolt.sweettalk.message.qq;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class HomeTimelineMsg extends Msg<ArticlePagingMsg> {

    ArticlePagingMsg data;

    public ArticlePagingMsg getData() {
        return data;
    }

    public void setData(ArticlePagingMsg data) {
        this.data = data;
    }
}
