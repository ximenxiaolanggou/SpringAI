package center.helloworld.openai.web;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("chat")
public class ChatController {

    @Resource
    private OpenAiChatModel chatModel;

    @GetMapping("c1")
    public Object chat1(String msg) {
        String res = chatModel.call(msg);
        return res;
    }

    @GetMapping("c2")
    public Object chat2(String msg) {
        ChatOptions chatOptions = ChatOptionsBuilder
                .builder()
                .withModel("gpt-3.5-turbo")
                .withTemperature(0.4F)//温度越高，回答得比较有创新性，但是准确率会下降，温度越低，回答的准确率会更好
                .build();
        ChatResponse res = chatModel.call(new Prompt(msg, chatOptions));
        return res;
    }


    /**
     * 调用OpenAI的接口
     *
     * @param msg 我们提的问题
     * @return
     */
    @RequestMapping(value = "c3")
    public Object chat4(@RequestParam(value = "msg") String msg) {
        //可选参数在配置文件中配置了，在代码中也配置了，那么以代码的配置为准，也就是代码的配置会覆盖掉配置文件中的配置
        Flux<ChatResponse> flux = chatModel.stream(new Prompt(msg, OpenAiChatOptions.builder()
                //.withModel("gpt-4-32k") //gpt的版本，32k是参数量
                .withTemperature(0.4F) //温度越高，回答得比较有创新性，但是准确率会下降，温度越低，回答的准确率会更好
                .build()));

        flux.toStream().forEach(chatResponse -> {
            System.out.println(chatResponse.getResult().getOutput().getContent());
        });
        return flux.collectList(); //数据的序列，一序列的数据，一个一个的数据返回
    }
}
