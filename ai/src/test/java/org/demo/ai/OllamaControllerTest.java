package org.demo.ai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
public class OllamaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void checkSpockExistInDB() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customer/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Spock")));
    }

    public void testNoMemoryLLM() {

    }

    public void testWithMemoryLLM() {

    }
}
