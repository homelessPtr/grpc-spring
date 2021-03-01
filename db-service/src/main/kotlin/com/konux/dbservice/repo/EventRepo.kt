package com.konux.dbservice.repo

import com.konux.dbservice.domainobject.EventDO
import org.springframework.data.repository.CrudRepository

interface EventRepo : CrudRepository<EventDO, Long> {
    fun findAllByUser(userId : Long): Iterable<EventDO>
}