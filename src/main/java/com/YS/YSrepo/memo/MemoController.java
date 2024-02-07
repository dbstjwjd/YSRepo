package com.YS.YSrepo.memo;

import com.YS.YSrepo.member.Member;
import com.YS.YSrepo.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Memo> memoList = memoService.getList();
        model.addAttribute("list", memoList);
        return "memo/memo";
    }

    @GetMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String create() {
        return "memo/write";
    }

    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String create(String title, String content, Principal principal) {
        Member member = memberService.getByUsername(principal.getName());
        memoService.createMemo(title, content, member);
        return "redirect:/memo/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Memo memo = memoService.getMemo(id);
        model.addAttribute("memo", memo);
        return "memo/detail";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, Model model, Principal principal) {
        Memo memo = memoService.getMemo(id);
        if (!memo.getMember().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        model.addAttribute("memo", memo);
        return "memo/write";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, Principal principal, String title, String content) {
        Memo memo = memoService.getMemo(id);
        if (!memo.getMember().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        memoService.modifyMemo(memo, title, content);
        return "redirect:/memo/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Memo memo = memoService.getMemo(id);
        memoService.deleteMemo(memo);
        return "redirect:/memo/list";
    }
}
