package com.resume.backend.resume_ai_backend.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class ResumeServiceImpl implements ResumeService{

    private ChatClient chatClient;

    public ResumeServiceImpl(ChatClient.Builder builder){
        this.chatClient= builder.build();
    }


    @Override
    public String generateResumeResponse(String userResumeDescription) throws IOException {

        String promptString=this.loadPromptFromFile("resume_prompt.txt");

        String promptContent=this.putValuesToTemplate(promptString,Map.of("userDescription",userResumeDescription));

        Prompt prompt=new Prompt("hello");

        var rawResponse = chatClient.prompt(prompt).call();
        //System.out.println("RAW RESPONSE: " + rawResponse);
        System.out.println("Content: " + rawResponse.content());


        String response=chatClient.prompt(prompt).call().content();

        return response;
    }
    String loadPromptFromFile(String filename) throws IOException {
        Path path =new ClassPathResource(filename).getFile().toPath();
        return Files.readString(path);
    }

    String putValuesToTemplate(String template, Map<String,String>values){
        for(Map.Entry<String,String>entry: values.entrySet()){
            template=template.replace("{{"+entry.getKey()+"}}",entry.getValue());
        }
        return  template;
    }

}
