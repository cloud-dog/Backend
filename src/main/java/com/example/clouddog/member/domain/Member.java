package com.example.clouddog.member.domain;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.comment.domain.LikeComment;
import com.example.clouddog.member.api.dto.request.MemberProfileUpdateReqDto;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String memberName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String name;

    private String picture;

    private String nickName;

    private int petNumber;

    private String petName;

    @Lob
    private String petDescription;

    private int mindCount;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberWriteBoard> memberWriteBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeComment> likeComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> friends = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return memberName;
    }

    @Builder
    private Member(String memberName, Role role, String email, String name, String picture, List<Friendship> friends) {
        this.memberName = memberName;
        this.role = role;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.friends = friends;
    }

    public void update(FirebaseToken token) {
        this.memberName = token.getUid();
        this.email = token.getEmail();
        this.name = token.getName();
        this.picture = token.getPicture();
    }

    public void profileUpdate(MemberProfileUpdateReqDto memberProfileUpdateReqDto) {
        this.nickName = memberProfileUpdateReqDto.nickName();
        this.petNumber = memberProfileUpdateReqDto.petNumber();
        this.petName = memberProfileUpdateReqDto.petName();
        this.petDescription = memberProfileUpdateReqDto.petDescription();
        this.mindCount = memberProfileUpdateReqDto.mindCount() * 30;
    }

    public void addComments(Board board, String cmContent, Long previousCommentId) {
        Comment comment = new Comment(this, board, cmContent, previousCommentId);
        this.comments.add(comment);
    }

    public void addBoards(Board board) {
        MemberWriteBoard memberWriteBoard = new MemberWriteBoard(this, board, board.getBoardTag());
        this.memberWriteBoards.add(memberWriteBoard);
    }

    public void deleteBoards(Board board) {
        MemberWriteBoard memberWriteBoard = findWriteBoard(board);
        this.memberWriteBoards.remove(memberWriteBoard);
    }

    private MemberWriteBoard findWriteBoard(Board board) {
        return memberWriteBoards.stream()
                .filter(memberWriteBoard -> memberWriteBoard.getBoard().equals(board))
                .findFirst()
                .orElse(null);
    }
}
