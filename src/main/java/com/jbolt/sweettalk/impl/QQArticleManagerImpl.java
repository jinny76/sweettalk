package com.jbolt.sweettalk.impl;

import com.google.gson.Gson;
import com.jbolt.sweettalk.domain.IArticle;
import com.jbolt.sweettalk.interfaces.ArticleManager;
import com.jbolt.sweettalk.message.qq.HomeTimelineMsg;
import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.utils.QHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class QQArticleManagerImpl implements ArticleManager {

    private AuthInfo authInfo = new AuthInfo();
    private static OAuthV2 oAuth = null;

    private Long lastAuthenTime = 0L;

    public static final String FORMAT = "json";

    private static QQArticleManagerImpl instance;

    public static QQArticleManagerImpl getInstance() {
        if (instance == null) {
            instance = new QQArticleManagerImpl();
        }
        return instance;
    }

    private QQArticleManagerImpl() {
    }

    public void doAuth() {
        if (oAuth == null) {
            oAuth = new OAuthV2();
            oAuth.setClientId(authInfo.getAppId());
            oAuth.setClientSecret(authInfo.getAppKey());
            oAuth.setRedirectUri(authInfo.getUrl());
            oAuth.setAccessToken(authInfo.getToken());
            oAuth.setAuthorizeCode(authInfo.getAuthenCode());
            oAuth.setOpenid(authInfo.getOpenId());
            oAuth.setOpenkey(authInfo.getOpenKey());
            oAuth.setExpiresIn(authInfo.getExpiresIn());
        }

        checkAuthInfo();
    }

    private void checkAuthInfo() {
        QHttpClient httpClient = new QHttpClient(2, 2, 5000, 5000, null, null);
        OAuthV2Client.setQHttpClient(httpClient);

        if (oAuth.getStatus() == 2) {
            System.out.println("Get Authorization Code failed!");
            if (System.currentTimeMillis() - lastAuthenTime > 1000 * 60 * 60 * 24) {
                askReauthen();
            } else {
                return;
            }
        }

        oAuth.setGrantType("authorize_code");
        try {
            boolean result = OAuthV2Client.accessToken(oAuth);
            if (!result) {
                System.out.println("Get Authorization Code failed!");
                if (System.currentTimeMillis() - lastAuthenTime > 1000 * 60 * 60 * 24) {
                    askReauthen();
                }
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oAuth.getStatus() == 3) {
            System.out.println("Get Access Token failed!");
            return;
        }

        authInfo.setToken(oAuth.getAccessToken());
        authInfo.storeConfig();
        httpClient.shutdownConnection();
    }

    private void askReauthen() {
        lastAuthenTime = System.currentTimeMillis();
        openBrowser(oAuth);
    }

    private void openBrowser(OAuthV2 oAuth) {
        String authorizationUrl = OAuthV2Client.generateAuthorizationURL(oAuth);

        if (!java.awt.Desktop.isDesktopSupported()) {
            System.err.println("Desktop is not supported (fatal)");
            System.exit(1);
        }

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        if (desktop == null || !desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
            System.err.println("Desktop doesn't support the browse action (fatal)");
            System.exit(1);
        }

        try {
            String authenCode = authInfo.getAuthenCode();
            desktop.browse(new URI(authorizationUrl));
            while (true && authenCode.equals(authInfo.getAuthenCode())) {
                Thread.sleep(1000);
                authInfo.initConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        String response =
            "code=" + authInfo.getAuthenCode() + "&openid=" + authInfo.getOpenId() + "&openkey=" + authInfo
                .getOpenKey();
        OAuthV2Client.parseAuthorization(response, oAuth);
        checkAuthInfo();
    }

    public List<IArticle> getHomeTimeline() {
        StatusesAPI statusesAPI = new StatusesAPI(oAuth.getOauthVersion());
        try {

            String json = statusesAPI.homeTimeline(oAuth, FORMAT, "0", "0", "10", "0", "0");
            System.out.println("json = " + json);
            HomeTimelineMsg msg = null;
            Gson gson = new Gson();
            if (json.contains("\"errcode\":0,")) {
                msg = gson.fromJson(json, HomeTimelineMsg.class);
                System.out.println("msg = " + msg);
                return new ArrayList<IArticle>(msg.getData().getInfo());
            } else {
                System.out.println("error.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            statusesAPI.shutdownConnection();
        }
    }

    public void updateAuthInfo(Map newAuthenInfo) {
        authInfo = new AuthInfo();
        authInfo.setAuthenCode((String) newAuthenInfo.get("authencode"));
        authInfo.setOpenId((String) newAuthenInfo.get("openid"));
        authInfo.setOpenKey((String) newAuthenInfo.get("openkey"));
        authInfo.storeConfig();
    }

    class AuthInfo {

        public static final String CONFIG_PATH = "qqauthinfo.properties";
        String appId;
        String appKey;
        String url;
        String token;
        String authenCode;
        String openId;
        String openKey;
        String expiresIn;
        Properties config;

        AuthInfo() {
            initConfig();
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getOpenKey() {
            return openKey;
        }

        public void setOpenKey(String openKey) {
            this.openKey = openKey;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Properties getConfig() {
            return config;
        }

        public String getAuthenCode() {
            return authenCode;
        }

        public void setAuthenCode(String authenCode) {
            this.authenCode = authenCode;
        }

        public void initConfig() {
            try {
                config = new Properties();
                final FileInputStream inputStream = new FileInputStream(new File(CONFIG_PATH));
                config.load(inputStream);
                appId = config.getProperty("appid");
                appKey = config.getProperty("appkey");
                url = config.getProperty("url");
                expiresIn = config.getProperty("expiresin");
                token = config.getProperty("token");
                authenCode = config.getProperty("authencode");
                openId = config.getProperty("openid");
                openKey = config.getProperty("openkey");
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void storeConfig() {
            try {
                final FileOutputStream outputStream = new FileOutputStream(new File(CONFIG_PATH));
                config.setProperty("appid", appId);
                config.setProperty("appkey", appKey);
                config.setProperty("url", url);
                config.setProperty("expiresin", expiresIn);
                config.setProperty("token", token);
                config.setProperty("authencode", authenCode);
                config.setProperty("openid", openId);
                config.setProperty("openkey", openKey);
                config.store(outputStream, "auth info");
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
