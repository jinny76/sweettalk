package com.jbolt.sweettalk.impl;

import com.jbolt.sweettalk.interfaces.ArticleManager;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class AuthenInfoUpdater extends HttpServlet {

    ArticleManager articleManager = QQArticleManagerImpl.getInstance();

    public AuthenInfoUpdater() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StringUtils.isNotBlank(req.getParameter("code"))) {
            final HashMap<String, String> info = new HashMap<String, String>();
            info.put("authencode", req.getParameter("code"));
            info.put("openid", req.getParameter("openid"));
            info.put("openkey", req.getParameter("openkey"));
            articleManager.updateAuthInfo(info);
            resp.getWriter().write("Update OK.");
        } else {
            resp.getWriter().write("OK.");
        }
    }
}
