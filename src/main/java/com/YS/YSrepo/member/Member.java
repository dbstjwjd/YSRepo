package com.YS.YSrepo.member;

import com.YS.YSrepo.library.Library;
import com.YS.YSrepo.memo.Memo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Library> dataList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Memo> memoList;

}
