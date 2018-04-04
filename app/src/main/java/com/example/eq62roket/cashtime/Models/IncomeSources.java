package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 4/1/18.
 */
@ParseClassName("Sources")
public class IncomeSources extends ParseObject {
    String parseId, name, userId;

    public String getParseId() {
        return parseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IncomeSources() {
    }
}
