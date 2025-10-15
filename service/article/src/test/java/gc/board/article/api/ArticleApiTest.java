package gc.board.article.api;
import gc.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient restclient = RestClient.create("http://localhost:9000");

    @Test
    void createTest(){
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response: " + response);
    }


    ArticleResponse create(ArticleCreateRequest request){
        return restclient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve() //api 호출!!
                .body(ArticleResponse.class);
    }
    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long brandId;
        private Long writerId;
    }

    @Test
    void updateTest(){
        update(229150395618684928L);

    }

    private void update(Long articleId) {
        restclient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 2", "my content 2"))
                .retrieve();

    }
    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }


    @Test
    void readTest() {
        Long articleId = 229150395618684928L;
        ArticleResponse result = read(articleId);
        System.out.println("response: " + result);
    }

    ArticleResponse read(Long articleId) {
        return restclient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void deleteTest() {
        Long articleId = 229150395618684928L;
        delete(articleId);
    }

    void delete(Long articleId) {
        restclient.delete()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve();
    }

}
