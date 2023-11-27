package com.example.clouddog.message.application;

import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.repository.MemberRepository;
import com.example.clouddog.member.exception.NotFoundMemberException;
import com.example.clouddog.message.api.dto.request.MessageDeleteReqDto;
import com.example.clouddog.message.api.dto.request.MessageReqDto;
import com.example.clouddog.message.api.dto.response.MessageResDto;
import com.example.clouddog.message.domain.Message;
import com.example.clouddog.message.domain.repository.MessageRepository;
import com.example.clouddog.message.exception.NotFoundMessageException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    public MessageService(MessageRepository messageRepository, MemberRepository memberRepository) {
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;
    }

    //메시지 전부 불러오기
    public List<MessageResDto> messageFind(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        List<Message> messages = messageRepository.findByMember(member);

        List<MessageResDto> messageResDtos = new ArrayList<>();
        for (Message message : messages) {
            messageResDtos.add(MessageResDto.from(message));
        }

        return messageResDtos;
    }

    //메세지 등록
    @Transactional
    public void messageSave(Long memberId, MessageReqDto messageReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMessageException::new);
        Message message = Message.of(member, messageReqDto);

        messageRepository.save(message);
    }

    //메세지 수정
    @Transactional
    public void messageUpdate(Long memberId, MessageReqDto messageReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Message message = messageRepository.findByMemberAndMessageId(member, messageReqDto.messageId());

        message.update(messageReqDto);
    }

    //메세지 삭제
    @Transactional
    public void messageDelete(Long memberId, MessageDeleteReqDto messageDeleteReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        messageRepository.deleteByMemberAndMessageId(member, messageDeleteReqDto.messageId());
    }
}
