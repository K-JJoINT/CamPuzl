package com.JJoINT.CamPuzl.domain.Comment.controller;

import com.JJoINT.CamPuzl.domain.Comment.dto.PubCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.dto.PubCommentResponseDTO;
import com.JJoINT.CamPuzl.domain.Comment.service.PubCommentService;
import com.JJoINT.CamPuzl.global.auth.dto.SecurityMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pubs/comment")
@RequiredArgsConstructor
public class PubCommentController {
    private final PubCommentService pubCommentService;

    @Operation(summary = "댓글 저장", description = "펍에 대한 새로운 댓글을 저장합니다")
    @GetMapping("/{pubid}")
    public ResponseEntity<Page<PubCommentResponseDTO>> getCommentsByPubId(@PathVariable Long pubid, Pageable pageable) {
        Page<PubCommentResponseDTO> comments = pubCommentService.getCommentsByPubId(pubid, pageable)
                .map(pubComment -> new PubCommentResponseDTO(
                        pubComment.getId(),
                        pubComment.getReview(),
                        pubComment.getRating(),
                        pubComment.getWriter()
                ));
        return ResponseEntity.ok(comments);
    }
    @Operation(summary = "댓글 저장", description = "펍에 대한 새로운 댓글을 저장합니다")
    @PostMapping("/save/{pubid}")
    public ResponseEntity<String> saveComment(@PathVariable Long pubid, @RequestBody PubCommentDTO commentDto, @AuthenticationPrincipal SecurityMemberDTO securityMemberDTO) {
        pubCommentService.saveComment(pubid, commentDto, securityMemberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment saved successfully");
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 ID로 삭제합니다")
    @DeleteMapping("/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentid) {
        pubCommentService.deleteComment(commentid);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
