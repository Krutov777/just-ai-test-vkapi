package ru.justai.vkbot.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.justai.vkbot.action.VkAction
import ru.justai.vkbot.dto.VkUnreadMessages
import ru.justai.vkbot.service.MessageService
import ru.justai.vkbot.util.Constants.RESULT_MESSAGE
import ru.justai.vkbot.util.LogMessage.INVALID_RESPONSE
import java.util.UUID


@Service
class MessageServiceImpl(
    private val vkAction: VkAction
) : MessageService {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun processReceivedMessages() {

        val vkUnreadMessages = vkAction.getUnreadMessages().block()

        if (isInvalidResponse(vkUnreadMessages)) {
            log.warn(INVALID_RESPONSE.format(vkUnreadMessages))
            return
        }

        vkUnreadMessages?.response?.items?.forEach { item ->
            val peerId = item.lastMessage.peerId
            val messageText = item.lastMessage.text
            val randomId = generateUniqueRandomId()

            sendMessageBack(peerId, randomId, messageText)
        }
    }

    private fun sendMessageBack(peerId: Int, randomId: Int, message: String) {
        val resultMessage = RESULT_MESSAGE.format(message)
        vkAction.sendMessage(peerId, randomId, resultMessage).block()
    }

    private fun isInvalidResponse(vkUnreadConversationsResponse: VkUnreadMessages?): Boolean {
        return vkUnreadConversationsResponse?.response == null
    }

    private fun generateUniqueRandomId(): Int {
        return UUID.randomUUID().hashCode()
    }
}