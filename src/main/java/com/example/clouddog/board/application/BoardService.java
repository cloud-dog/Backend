package com.example.clouddog.board.application;

import com.example.clouddog.board.api.dto.request.BoardReqDto;
import com.example.clouddog.board.api.dto.response.BoardListResDto;
import com.example.clouddog.board.api.dto.response.BoardResDto;
import com.example.clouddog.board.domain.Board;
import com.example.clouddog.board.domain.repository.BoardRepository;
import com.example.clouddog.board.exception.NotFoundBoardException;
import com.example.clouddog.comment.api.dto.response.CommentResDto;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.comment.domain.LikeComment;
import com.example.clouddog.comment.domain.repository.CommentRepository;
import com.example.clouddog.comment.domain.repository.LikeCommentRepository;
import com.example.clouddog.image.domain.Image;
import com.example.clouddog.image.domain.repository.ImageRepository;
import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.MemberWriteBoard;
import com.example.clouddog.member.domain.repository.MemberRepository;
import com.example.clouddog.member.domain.repository.MemberWriteBoardRepository;
import com.example.clouddog.member.exception.NotFoundMemberException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final MemberWriteBoardRepository memberWriteBoardRepository;

    // 게시글 저장
    @Transactional
    public void boardAndImageSave(Long memberId, BoardReqDto boardReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Image image = imageRepository.findById(boardReqDto.imageId()).orElseThrow(NotFoundBoardException::new);

        Board board = Board.of(boardReqDto, image);

        member.addBoards(board);
        boardRepository.save(board);
    }

    // 이미지가 없을 때 게시글 저장
    @Transactional
    public void boardNotImageSave(Long memberId, BoardReqDto boardReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        Board board = Board.of(boardReqDto, null);

        member.addBoards(board);
        boardRepository.save(board);
    }

    // 게시글 상세보기_1개 자세히 불러오기
    public BoardResDto findById(Long memberId, Long boardId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoardException::new);

        List<Comment> boardComments = commentRepository.findByBoard(board);

        List<CommentResDto> comments = new ArrayList<>();
        for (Comment comment : boardComments) {
            List<LikeComment> likeComments = likeCommentRepository.findByComment(comment);

            List<Long> likeCommentMembers = new ArrayList<>();
            for (LikeComment likeComment : likeComments) {
                likeCommentMembers.add(likeComment.getMember().getMemberId());
            }
            comments.add(CommentResDto.of(comment, likeCommentMembers));
        }

        if (board.getImage() == null) {
            return BoardResDto.notImageOf(board, comments);
        } else {
            return BoardResDto.of(board, comments);
        }
    }

    // 친구한테 보여주는 그것도 손 좀 봐야겠는데, 친구가 아닌데도, 경로 타고 그냥 막 들어올 수 도 있음.
    // -> FriendShip에서 내 memberId랑 친구의 memberId 비교해서 있으면 리스트 반환해주고 없으면 친구가 아니라는 표시 해주기
    public Page<BoardListResDto> findAllPage(Long memberId, int page, int size) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        Page<MemberWriteBoard> boardListPage = memberWriteBoardRepository.findByMember(member,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "memberWriteBoardId")));

        return boardListPage.map(this::boardListMap);
    }

    public Page<BoardListResDto> findByTagPage(Long memberId, int tag, int page, int size) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        Page<MemberWriteBoard> boardListPage = memberWriteBoardRepository.findByMemberAndTag(member, tag,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "memberWriteBoardId")));

        return boardListPage.map(this::boardListMap);
    }

    private BoardListResDto boardListMap(MemberWriteBoard memberWriteBoard) {
        if (memberWriteBoard.getBoard().getImage() != null) {
            return new BoardListResDto(
                    memberWriteBoard.getBoard().getBoardId(),
                    memberWriteBoard.getMember().getMemberId(),
                    memberWriteBoard.getBoard().getBoardTitle(),
                    memberWriteBoard.getTag(),
                    memberWriteBoard.getBoard().getImage().getImageUrl()
            );
        } else {
            return new BoardListResDto(
                    memberWriteBoard.getBoard().getBoardId(),
                    memberWriteBoard.getMember().getMemberId(),
                    memberWriteBoard.getBoard().getBoardTitle(),
                    memberWriteBoard.getTag(),
                    null
            );
        }
    }

    // 게시글 수정
    @Transactional
    public void boardUpdate(Long boardId, BoardReqDto boardReqDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoardException::new);
        board.update(boardReqDto);
    }

    //게시글 삭제
    @Transactional
    public void boardDelete(Long memberId, Long bdId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Board board = boardRepository.findById(bdId).orElseThrow(NotFoundBoardException::new);

        member.deleteBoards(board);
        memberRepository.save(member);
    }
}
