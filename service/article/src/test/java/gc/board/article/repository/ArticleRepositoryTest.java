package gc.board.article.repository;

import gc.board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j // 로그를 남길 수 있는 어노테이션
@SpringBootTest // 스프링을 이용해서 통합테스트할 수 있는 어노테이션
class ArticleRepositoryTest {
    @Autowired // 레포지토리를 테스트하기 위해 불러옴.
    ArticleRepository articleRepository;


    @Test
    void findAllTest(){
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);
        log.info("articles.size = {}",articles.size());
        for(Article article : articles){
            log.info("article = {}",article.getArticleId());
        }
    }
    @Test
    void countTest(){
        Long count = articleRepository.count(1L, 10000L);
        log.info("count = {}",count);
    }

    @Test
    void findInfiniteScrollTest(){
        List<Article> articles = articleRepository.findAllInfiniteScroll(1L, 30L);
        for (Article article : articles) {
            log.info("articleId = {}",article.getArticleId());
        }
        //lastArticleId = 229162444276363264
        Long lastArticleId = articles.getLast().getArticleId();
        List<Article> articles2 = articleRepository.findAllInfiniteScroll(1L ,30L, lastArticleId );
        for(Article article : articles2){
            log.info("articleId = {}",article.getArticleId());
    }

    }
}