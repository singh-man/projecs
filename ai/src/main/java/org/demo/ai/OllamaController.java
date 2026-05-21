package org.demo.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.transformer.ContentFormatTransformer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ollama")
@CrossOrigin("*")
@Configuration
public class OllamaController {

    private ChatClient chatClientNoMemory;
    private ChatClient chatClientWithMemory;

    public OllamaController(OllamaChatModel chatModel) {
//        this.chatClientNoMemory = ChatClient.create(chatModel);
        this.chatClientWithMemory = ChatClient.builder(chatModel)
                .defaultAdvisors(promptChatMemoryAdvisor())
                .defaultSystem("Act like a colleague")
                .build();
    }

    @GetMapping("/{message}")
    public ResponseEntity<String> noMemoryLLM(@PathVariable String message) {
        new ContentFormatTransformer(null, false)
                .andThen(new ContentFormatTransformer(null, false))
                .andThen(e -> e)
                .apply(null);
        ChatResponse chatResponse = chatClientNoMemory
                .prompt(message)
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response = chatResponse.getResult().getOutput().getText();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user}")
    String memoryLLM(@PathVariable String user, @RequestParam String question) {
        return chatClientWithMemory
                .prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
                .call()
                .content();
    }

//    @Bean
    static PromptChatMemoryAdvisor promptChatMemoryAdvisor(/*DataSource dataSource*/) {
//        var jdbc = JdbcChatMemoryRepository
//                .builder()
//                .dataSource(dataSource)
//                .build();

        var chatMessageWindow = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .build();

        return PromptChatMemoryAdvisor
                .builder(chatMessageWindow)
                .build();
    }
//    @Bean
//    McpSyncClient mcpSyncClient() {
//        var mcp = McpClient
//                .sync(HttpClientSseClientTransport.builder("http://localhost:8081").build()).build();
//        mcp.initialize();
//        return mcp;
//    }
}