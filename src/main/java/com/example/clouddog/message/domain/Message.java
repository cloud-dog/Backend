package com.example.clouddog.message.domain;


import com.example.clouddog.member.domain.Member;
import com.example.clouddog.message.api.dto.request.MessageReqDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String messageContent;

    private String messageTime;

    public Message(Member member, String messageContent) {
        this.member = member;
        this.messageContent = messageContent;
        LocalDateTime msgTime = LocalDateTime.now();
        this.messageTime = msgTime.toString();
    }

    public void update(MessageReqDto messageReqDto) {
        this.messageContent = messageReqDto.getMsgContent();
        LocalDateTime msgTime = LocalDateTime.now();
        this.messageTime = msgTime.toString();
    }
}
