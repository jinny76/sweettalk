package com.jbolt.sweettalk.domain.qq;

import com.jbolt.sweettalk.domain.IVideo;

import java.io.Serializable;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class Video implements Serializable, IVideo {

    String picurl;
    String player;
    String realurl;
    String shorturl;
    String title;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getRealurl() {
        return realurl;
    }

    public void setRealurl(String realurl) {
        this.realurl = realurl;
    }

    public String getShorturl() {
        return shorturl;
    }

    public void setShorturl(String shorturl) {
        this.shorturl = shorturl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
