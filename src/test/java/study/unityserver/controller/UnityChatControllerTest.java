package study.unityserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@AutoConfigureMockMvc
class UnityChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void uploadImage() throws Exception {
        Path path = Paths.get("/home/cjftn/IdeaProjects/unityServer/src/main/resources/hello.png");
        byte[] content = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "hello.png",
                "image/png",
                content
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/unity/uploadImage")
                        .file(file))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}