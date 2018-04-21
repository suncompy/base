/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package com.base.message.service.jpa.repository;

import com.base.message.service.jpa.entity.IMGroupMessage3;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.blt.talk.service.jpa.entity.IMGroupMessage3;

/**
 * im_group_message表对应JPARepository
 * 
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
public interface IMGroupMessage3Repository extends PagingAndSortingRepository<IMGroupMessage3, Long>, JpaSpecificationExecutor<IMGroupMessage3> {

}
