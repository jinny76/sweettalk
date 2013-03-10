package com.jbolt.sweettalk.interfaces;

import com.jbolt.sweettalk.domain.IArticle;

import java.util.List;
import java.util.Map;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public interface ArticleManager {


    List<IArticle> getHomeTimeline();

    void doAuth();

    void updateAuthInfo(Map newAuthenInfo);

}
