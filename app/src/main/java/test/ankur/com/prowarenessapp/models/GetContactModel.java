package test.ankur.com.prowarenessapp.models;

import java.util.List;

/**
 * Created by ankur.siwach on 5/27/2017.
 */

public class GetContactModel {

    private Object message;
    private String status;
    private List<Result> result = null;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
