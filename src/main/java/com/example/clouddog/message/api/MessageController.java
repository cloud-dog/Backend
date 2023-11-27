package com.example.clouddog.message.api;

import com.example.clouddog.message.api.dto.request.MessageDeleteReqDto;
import com.example.clouddog.message.api.dto.request.MessageReqDto;
import com.example.clouddog.message.api.dto.response.MessageResDto;
import com.example.clouddog.message.application.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "본인 메시지 리스트 불러오기", description = "본인의 메시지 리스트를 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "본인 메시지 리스트 불러오기 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/{memberId}/messages")
    public ResponseEntity<List<MessageResDto>> findAllMessages(@PathVariable(name = "memberId") Long memberId) {
        return new ResponseEntity<>(messageService.messageFind(memberId), HttpStatus.OK);
    }

    @Operation(summary = "메시지 작성", description = "메시지를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{memberId}/messages")
    public ResponseEntity<String> addMessage(@PathVariable(name = "memberId") Long memberId,
                                             @RequestBody MessageReqDto messageReqDto) {
        messageService.messageSave(memberId, messageReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "메시지 수정", description = "메시지를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PatchMapping("/{memberId}/messages")
    public ResponseEntity<String> updateMessage(@PathVariable(name = "memberId") Long memberId,
                                                @RequestBody MessageReqDto messageReqDto) {
        messageService.messageUpdate(memberId, messageReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "메시지 삭제", description = "메시지를 삭합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/{memberId}/messages")
    public ResponseEntity<String> deleteMessage(@PathVariable(name = "memberId") Long memberId,
                                                @RequestBody MessageDeleteReqDto messageDeleteReqDto) {
        messageService.messageDelete(memberId, messageDeleteReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
