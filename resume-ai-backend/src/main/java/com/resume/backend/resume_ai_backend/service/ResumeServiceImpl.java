package com.resume.backend.resume_ai_backend.service;

import org.json.JSONObject;
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

        Prompt prompt=new Prompt(promptContent);

//        var rawResponse = chatClient.prompt(prompt).call();
//        System.out.println("RAW RESPONSE: " + rawResponse);
//        System.out.println("Content: " + rawResponse.content());


        String response=chatClient.prompt(prompt).call().content();
System.out.println(response);
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

    public  static JSONObject parseMultipleResponses(String response){
        JSONObject jsonResponse=new JSONObject();

        int thinkStart=response.indexOf("<think")+7;
        int thinkEnd=response.lastIndexOf("</think");
        if(thinkStart!=-1 && thinkEnd!=-1){
            String thinkContent=response.substring(thinkStart,thinkEnd).trim();
            jsonResponse.put("think",thinkContent);
        }else{
            jsonResponse.put("think",JSONObject.NULL);
        }


        return jsonResponse;
    }

}
