package center.helloworld.openai.web;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.ai.openai.metadata.audio.OpenAiAudioSpeechResponseMetadata;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhishun.cai
 * @date 2024/8/29
 */

@Slf4j
@RestController
@RequestMapping("audio")
public class AudioController {

    @Resource
    private OpenAiAudioTranscriptionModel transcriptionModel;

    @Resource
    private OpenAiAudioSpeechModel speechModel;

    /**
     * 语音转文本
     * @param
     * @return
     */
    @GetMapping("a1")
    public Object chat1() {

        ClassPathResource fileSystemResource = new ClassPathResource("cat.mp3");

        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(fileSystemResource);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
        return response.getResult().getOutput();
    }

    /**
     * 文本转语音
     * @param
     * @return
     */
    @GetMapping("a2")
    public Object chat2(String msg) throws IOException{

        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withSpeed(1.0f)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt("hello everybody", speechOptions);
        SpeechResponse response = speechModel.call(speechPrompt);

        byte[] responseAsBytes = response.getResult().getOutput();

        FileOutputStream fos = new FileOutputStream("d:\\a.mp3");
        fos.write(responseAsBytes);
        fos.close();
        fos.close();
        return "success";
    }

}
