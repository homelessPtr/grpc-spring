package com.konux.eventservice.controller;

import com.konux.eventservice.controller.model.BaseResponse;
import com.konux.eventservice.controller.model.Event;
import com.konux.eventservice.service.ISenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final ISenderService service;

    @PostMapping
    public BaseResponse create(@RequestBody Event event) {
        return service.send(event);
    }

}
