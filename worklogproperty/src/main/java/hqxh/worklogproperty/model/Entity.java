package hqxh.worklogproperty.model;

import java.io.Serializable;

/**
 * Created by apple on 15/10/.
 */
public class Entity<T> implements Serializable {
    private T t;

    public Entity(T oT) {
        this.t = oT;
    }

    public Entity() {
    }

    public String getClassType() {
        return t.getClass().getName();
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
