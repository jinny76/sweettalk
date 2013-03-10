package com.jbolt.sweettalk.message.qq;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public abstract class PagingMsg<T> implements Serializable {

    Integer hasnext;

    public Integer getHasnext() {
        return hasnext;
    }

    public void setHasnext(Integer hasnext) {
        this.hasnext = hasnext;
    }

    public abstract List<T> getInfo();

    public abstract void setInfo(List<T> info);
}
