package study.unityserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.unityserver.entity.Openai_log;

import java.util.List;

public interface Openai_logRepository extends JpaRepository<Openai_log, Long> {
    List<Openai_log> findByRoleOrderByTimestampAsc(String role);
    //출력시 role 기준으로 검색하여 시간 순서대로 정렬
    List<Openai_log> findByIpOrderByTimestampAsc(String role);
    //ip 기준으로
}
