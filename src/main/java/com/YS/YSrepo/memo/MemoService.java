package com.YS.YSrepo.memo;

import com.YS.YSrepo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public void createMemo(String title, String content, Member member) {
        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setContent(content);
        memo.setCreateDate(LocalDateTime.now());
        memo.setMember(member);
        memoRepository.save(memo);
    }

    public List<Memo> getList() {
        return memoRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }

    public Memo getMemo(Integer id) {
        Optional<Memo> memo = memoRepository.findById(id);
        if (memo.isPresent()) return memo.get();
        else throw new RuntimeException("존재하지 않는 데이터입니다.");
    }

    public void modifyMemo(Memo memo, String title, String content) {
        memo.setTitle(title);
        memo.setContent(content);
        memo.setModifyDate(LocalDateTime.now());
        memoRepository.save(memo);
    }

    public void deleteMemo(Memo memo) {
        memoRepository.delete(memo);
    }
}
