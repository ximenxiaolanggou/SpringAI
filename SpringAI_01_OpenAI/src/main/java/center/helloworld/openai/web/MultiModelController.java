package center.helloworld.openai.web;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 多模态
 * @author zhishun.cai
 * @date 2024/8/29
 */

@Slf4j
@RestController
@RequestMapping("mutil")
public class MultiModelController {

    @Resource
    private OpenAiChatModel chatModel;

    @GetMapping("m1")
    public Object m1(String msg, String img) {
        UserMessage message = new UserMessage(msg, List.of(new Media(MediaType.IMAGE_PNG, img)));
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions
                .builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_0125_PREVIEW.getValue())
                .build();
        ChatResponse res = chatModel.call(new Prompt(message, openAiChatOptions));
        return res.getResult();
    }

}
