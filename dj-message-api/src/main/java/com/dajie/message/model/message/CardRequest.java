package com.dajie.message.model.message;

import com.dajie.message.model.user.SimpleUserView;

/**
 * Created by wills on 5/14/14.
 */
public class CardRequest extends SystemContent {

    private SimpleUserView userView;

    public CardRequest(SimpleUserView userView) {
        this.userView = userView;
    }

    public SimpleUserView getUserView() {
        return userView;
    }

    public void setUserView(SimpleUserView userView) {
        this.userView = userView;
    }
}
