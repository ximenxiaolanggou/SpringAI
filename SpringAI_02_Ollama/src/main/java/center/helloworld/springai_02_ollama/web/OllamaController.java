package center.helloworld.springai_02_ollama.web;

import jakarta.annotation.Resource;
import org.springframework.ai.autoconfigure.ollama.OllamaConnectionDetails;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhishun.cai
 * @date 2024/9/2
 */

@RestController
@RequestMapping("ollama")
public class OllamaController {

    @Resource
    private OllamaChatModel chatModel;

    /**
     * 聊天
     * @param msg
     * @return
     */
    @GetMapping("c1")
    public Object chat(String msg) {
        String res = chatModel.call(msg);
        return res;
    }


}
