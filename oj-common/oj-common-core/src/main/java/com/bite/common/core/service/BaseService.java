package com.bite.common.core.service;

import com.bite.common.core.domain.R;

public class BaseService {

    public R<Void> toR(int rows){
        return rows > 0 ? R.ok() : R.fail();
    }

    public R<Void> toR(boolean result){
        return result ? R.ok() : R.fail();
    }
}