package gc.board.article.api;
import gc.board.article.service.response.ArticlePageResponse;
import gc.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

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
    void readAllTest() {
        ArticlePageResponse response = restclient.get()
                .uri("/v1/articles?boardId=1&page=1&pageSize=30")
                .retrieve()
                .body(ArticlePageResponse.class);
        System.out.println("response.getArticleCount = " + response.getArticleCount());
        for(ArticleResponse article : response.getArticles()) {
            System.out.println("articleId = " + article.getArticleId());
        }
    }
    @Test
    void readAllInfiniteScrollTest(){
        List<ArticleResponse> articles1 = restclient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });
        System.out.println("fristPage");
        for(ArticleResponse article : articles1) {
            System.out.println("articleId = " + article.getArticleId());

        }
        System.out.println("===============================");

        Long lastArticleId = articles1.getLast().getArticleId();

        List<ArticleResponse> articles2 = restclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/articles/infinite-scroll")
                        .queryParam("boardId", 1)
                        .queryParam("pageSize", 5)
                        .queryParam("lastArticleId", lastArticleId)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {});

        System.out.println("=== 2nd Page ===");
        for (ArticleResponse article : articles2) {
            System.out.println("articleId = " + article.getArticleId());
        }


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
