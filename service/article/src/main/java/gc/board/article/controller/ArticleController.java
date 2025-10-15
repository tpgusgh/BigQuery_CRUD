package gc.board.article.controller;


import gc.board.article.entity.Article;
import gc.board.article.service.ArticleService;
import gc.board.article.service.request.ArticleCreateRequest;
import gc.board.article.service.request.ArticleUpdateRequest;
import gc.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class ArticleController {
    private final ArticleService articleService;


    // 읽기
    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }
    // 생성
    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody ArticleCreateRequest request) {
        return articleService.create(request);
    }
    // 수정
    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(
                        @PathVariable Long articleId, @RequestBody
                        ArticleUpdateRequest request) {
        return articleService.update(articleId, request);
    }
    // 삭제
    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }
}
