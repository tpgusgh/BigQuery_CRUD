package gc.board.article.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
//경우에 따라 301,601,901 등으로 계산해주는 유틸리티 클래스
public class PageLimitCalculator {
    public static Long calculatePageLimit(Long page, Long pageSize, Long movablePageCount){
        return (((page - 1 ) / movablePageCount) + 1) * pageSize * movablePageCount + 1;
    }
}
