package ru.justai.vkbot.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.justai.vkbot.service.MessageService
import java.util.concurrent.TimeUnit

@Component
class MessageScheduler(
    private val messageService: MessageService
) {
    @Scheduled(fixedDelayString = "\${app.scheduler.delay}", timeUnit = TimeUnit.SECONDS)
    fun processReceivedMessages() {
        messageService.processReceivedMessages()
    }
}