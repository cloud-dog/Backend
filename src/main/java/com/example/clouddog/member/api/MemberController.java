package com.example.clouddog.member.api;

import com.example.clouddog.member.api.dto.request.FriendSaveReqDto;
import com.example.clouddog.member.api.dto.request.MemberProfileUpdateReqDto;
import com.example.clouddog.member.api.dto.respnse.MemberResDto;
import com.example.clouddog.member.application.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/my-info")
    public ResponseEntity<MemberResDto> memberInfo(@RequestParam(name = "uid") String uid) {
        return new ResponseEntity<>(memberService.memberInfo(uid), HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<String> myProfileUpdate(@PathVariable(name = "memberId") Long memberId,
                                                  @RequestBody MemberProfileUpdateReqDto memberProfileUpdateReqDto) {
        memberService.myProfileUpdate(memberId, memberProfileUpdateReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/{memberId}/friend/info")
    public ResponseEntity<MemberResDto> friendInfo(@PathVariable(name = "memberId") Long memberId,
                                                   @RequestParam(name = "friendEmail") String friendEmail) {
        return new ResponseEntity<>(memberService.friendInfo(friendEmail), HttpStatus.OK);
    }

    @PostMapping("/{memberId}/friend")
    public ResponseEntity<String> friendAdd(@PathVariable(name = "memberId") Long memberId,
                                            @RequestBody FriendSaveReqDto friendSaveReqDto) {
        memberService.addFriend(memberId, friendSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/{memberId}/friends")
    public ResponseEntity<Page<MemberResDto>> friendsInfoList(@PathVariable(name = "memberId") Long memberId,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "4") int size) {
        return new ResponseEntity<>(memberService.friendsInfoList(memberId, page, size), HttpStatus.OK);
    }

}
