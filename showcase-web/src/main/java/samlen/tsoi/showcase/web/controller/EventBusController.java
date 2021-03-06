package samlen.tsoi.showcase.web.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samlen.tsoi.showcase.common.pojo.dto.Result;
import samlen.tsoi.showcase.web.entity.dto.AsyncEventBusDTO;
import samlen.tsoi.showcase.web.entity.dto.EventBusDTO;

/**
 * @author samlen_tsoi
 * @date 2018/12/7
 */
@RestController
@RequestMapping("eventBus")
public class EventBusController {
    @Autowired
    private EventBus eventBus;

    @Autowired
    private AsyncEventBus asyncEventBus;

    @GetMapping("sync")
    public Result sync() {
        eventBus.post(new EventBusDTO());
        eventBus.post(new EventBusDTO());
        eventBus.post(new EventBusDTO());
        eventBus.post(new EventBusDTO());
        return Result.ok();
    }

    @GetMapping("async")
    public Result async() {
        asyncEventBus.post(new AsyncEventBusDTO());
        asyncEventBus.post(new AsyncEventBusDTO());
        asyncEventBus.post(new AsyncEventBusDTO());
        asyncEventBus.post(new AsyncEventBusDTO());
        return Result.ok();
    }
}
