package com.example.clouddog.message.application;

import com.example.clouddog.board.exception.NotFoundMemberException;
import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.repository.MemberRepository;
import com.example.clouddog.message.api.dto.request.MessageReqDto;
import com.example.clouddog.message.api.dto.response.MessageResDto;
import com.example.clouddog.message.domain.Message;
import com.example.clouddog.message.domain.repository.MessageRepository;
import com.example.clouddog.message.exception.NotFoundMessageException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

        Stream<Message> returnList = messageRepository.findMessageByMember(member);

        return returnList.map(this::messageListMap).collect(Collectors.toList());
    }

    //메세지 등록
    @Transactional
    public void messageSave(MessageReqDto messageDto) {
        Member member = memberRepository.findById(messageDto.getMemberId()).orElseThrow(NotFoundMessageException::new);
        Message message = new Message(member, messageDto.getMsgContent());
        messageRepository.save(message);
    }

    //메세지 수정
    @Transactional
    public void messageUpdate(Long msgId, MessageReqDto updateMsg) {
        Message message = messageRepository.findById(msgId).orElseThrow(NotFoundMessageException::new);
        messageRepository.findById(msgId).orElseThrow(NotFoundMessageException::new).update(updateMsg);
    }

    //메세지 삭제
    @Transactional
    public void messageDelete(Long msgId) {
        messageRepository.deleteById(msgId);
    }

    //메세지 dto매핑
    public MessageResDto messageListMap(Message message){
        return new MessageResDto(
                message.getMessageId(),
                message.getMessageContent(),
                message.getMessageTime()
        );
    }
}
