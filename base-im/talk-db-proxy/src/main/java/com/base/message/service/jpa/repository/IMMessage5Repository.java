/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package com.base.message.service.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.blt.talk.service.jpa.entity.IMMessage5;

/**
 * im_message表对应JPARepository
 * 
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
public interface IMMessage5Repository extends PagingAndSortingRepository<IMMessage5, Long>, JpaSpecificationExecutor<IMMessage5> {

}
