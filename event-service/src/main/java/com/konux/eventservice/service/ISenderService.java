package com.konux.eventservice.service;

import com.konux.eventservice.controller.model.BaseResponse;
import com.konux.eventservice.controller.model.Event;

public interface ISenderService {

    BaseResponse send(Event event);

}
