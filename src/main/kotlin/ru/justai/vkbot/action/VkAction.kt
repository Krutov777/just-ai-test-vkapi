package ru.justai.vkbot.action

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import ru.justai.vkbot.config.VkConfig
import ru.justai.vkbot.dto.VkUnreadMessages
import ru.justai.vkbot.util.LogMessage.VK_API_REQUEST_ERROR

@Component
class VkAction(
    private val webClient: WebClient,
    private val vkConfig: VkConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun getUnreadMessages(): Mono<VkUnreadMessages> {
        val conversationsUrl: String = String.format(
            vkConfig.vkUnreadMessageUrl,
            vkConfig.messageCount,
            vkConfig.vkApiKey,
            vkConfig.vkApiVersion
        )

        return webClient.get()
            .uri(conversationsUrl)
            .retrieve()
            .bodyToMono(VkUnreadMessages::class.java)
            .onErrorResume(WebClientResponseException::class.java) { e ->
                log.error(VK_API_REQUEST_ERROR, e)
                Mono.empty()
            }
    }

    fun sendMessage(peerId: Int, randomId: Int, message: String): Mono<Void> {
        val sendUrl: String = String.format(
            vkConfig.vkSendMessageUrl,
            vkConfig.vkApiKey,
            peerId,
            randomId,
            message,
            vkConfig.vkApiVersion
        )

        return webClient.post()
            .uri(sendUrl)
            .retrieve()
            .bodyToMono(Void::class.java)
    }
}