package com.if26.leuks.safelock.tool;

/**
 * Created by Leuks on 22/11/2016.
 */

import java.io.Serializable;

/**
 * Modélise des tuples génériques de deux valeurs
 *
 * @param <S>
 * @param <I>
 */
public class Tuple<S, I> implements Serializable {
    private S arg1;
    private I arg2;

    /**
     * Construit un Tuple
     *
     * @param s
     * @param i
     */
    public Tuple(S s, I i) {
        arg1 = s;
        arg2 = i;
    }

    public Tuple() {
    }


    @Override
    public boolean equals(Object obj) {
        boolean isEquals = true;
        if (!(obj instanceof Tuple)) isEquals = false;
        if (arg1 == null) {
            if (arg1 != ((Tuple) obj).arg1) isEquals = false;
        } else {
            if (!(arg1.equals(((Tuple) obj).arg1))) isEquals = false;
        }
        if (arg2 == null) {
            if (arg2 != ((Tuple) obj).arg2) isEquals = false;
        } else {
            if (!(arg2.equals(((Tuple) obj).arg2))) isEquals = false;
        }
        return isEquals;
    }

    public String toString(String separator) {
        return String.valueOf(arg1) + separator + String.valueOf(arg2);
    }

    //GS
    public S getArg1() {
        return arg1;
    }

    public void setArg1(S arg1) {
        this.arg1 = arg1;
    }

    public I getArg2() {
        return arg2;
    }

    public void setArg2(I arg2) {
        this.arg2 = arg2;
    }

}
