package com.jbolt.sweettalk.domain.qq;

import com.jbolt.sweettalk.domain.IUser;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class User implements IUser {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
