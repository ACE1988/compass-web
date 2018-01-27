package com.sdxd.api.vo.credit;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by lenovo on 2017/7/25.
 */
public class UpLoadStatus {

    private List<Object> errors = Lists.newArrayList();

    public void addError(String error) {
        errors.add(error);
    }

    public void addError(Object error) {
        errors.add(error);
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    public List<Object> getErrors() {
        return errors;
    }
}
