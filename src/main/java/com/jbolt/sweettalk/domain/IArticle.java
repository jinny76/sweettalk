package com.jbolt.sweettalk.domain;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public interface IArticle {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getNick();

    void setNick(String nick);

    String getOpenid();

    void setOpenid(String openid);

    String getOrigtext();

    void setOrigtext(String origtext);

    Integer getSelf();

    void setSelf(Integer self);

    IArticle getSource();

    Integer getStatus();

    void setStatus(Integer status);

    String getText();

    void setText(String text);

    Long getTimestamp();

    void setTimestamp(Long timestamp);

    Integer getType();

    void setType(Integer type);

    IUser getUser();
}
