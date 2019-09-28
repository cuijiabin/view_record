package com.rose.bob.service;

import com.rose.bob.domain.FreightInsureOrderLog;

public interface FreightInsureOrderLogService {

    FreightInsureOrderLog selectByPrimaryKey(Integer id);

    FreightInsureOrderLog copyByPrimaryKey(Integer id);

    FreightInsureOrderLog insertByPrimaryKey(Integer id);
}
