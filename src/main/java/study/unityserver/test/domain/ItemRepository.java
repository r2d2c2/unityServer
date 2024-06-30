package study.unityserver.test.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {//데이터 베이스는 없으나 대충 있다고 생각하고 저장
    private final Map<Long,Item> store=new HashMap<>();
    private long sequence=0l;//key

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }
    public Item findById(Long id){
        return store.get(id );//저장한 객체 찾기
    }
}
