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
            log.info("article = {}",article);
        }
    }

}