package gc.board.article.service;

import gc.board.article.entity.Article;
import gc.board.article.repository.ArticleRepository;
import gc.board.article.service.request.ArticleCreateRequest;
import gc.board.article.service.request.ArticleUpdateRequest;
import gc.board.article.service.response.ArticlePageResponse;
import gc.board.article.service.response.ArticleResponse;
import jakarta.transaction.Transactional;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


//핵심 비지니스 로직 코드 작성
//근데 DTO라는 재료가 필요함
//DTO란 컨트롤러에 주고받는 요청 / 응답 타입

@Service
@RequiredArgsConstructor

public class ArticleService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;


    //생성
    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = articleRepository.save(
            Article.create(
                    snowflake.nextId(), request.getTitle(),
                    request.getContent(), request.getBrandId(),
                    request.getWriterId()
            )
        );
        return ArticleResponse.from(article);
    }
    //수정
    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.from(article);


    }
    //읽기
    public ArticleResponse read(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        return ArticleResponse.from(article);
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize,pageSize).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(
                        boardId,
                        PageLimitCalculator.calculatePageLimit(page, pageSize,  10L)
                )
        );

    }

    //무한 스크롤 조회 로직
    public List<ArticleResponse> readAllInfiniteScroll(
            Long boardId, Long pageSize, Long lastArticleId
    ) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);

        return articles.stream()
                .map(ArticleResponse::from)
                .toList();
    }


    //삭제
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }


}
