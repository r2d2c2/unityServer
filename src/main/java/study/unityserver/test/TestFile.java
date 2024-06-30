package study.unityserver.test;

import org.springframework.stereotype.Controller;

@Controller
public class TestFile {
    /*@PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws Exception {
        String url = "http://127.0.0.1:7070/upload";

        // Convert MultipartFile to File
        File convFile = new File(image.getOriginalFilename());
        image.transferTo(convFile);

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set up the request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(convFile));

        // Set up the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the network request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Clean up the temporary file
        convFile.delete();

        return response.getBody();
    }*/
}
