package center.helloworld.openai.web;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author zhishun.cai
 * @date 2024/8/29
 */

@Slf4j
@RestController
@RequestMapping("img")
public class ImageController {

    @Resource
    private OpenAiImageModel imageModel;

    @GetMapping("i1")
    public Object chat1(String msg) {
        ImageOptions imageOptions = ImageOptionsBuilder
                .builder()
                .withModel(OpenAiImageApi.ImageModel.DALL_E_3.getValue())
                .withWidth(1024)
                .withHeight(1792)
                .build();
        ImageResponse res = imageModel.call(new ImagePrompt(msg,imageOptions));
        System.out.println(res);

        return res.getResult();
    }

}
