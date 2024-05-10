package com.JJoINT.CamPuzl.domain.Comment.controller;

import com.JJoINT.CamPuzl.domain.Comment.domain.PubComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.PubCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.dto.PubCommentResponseDTO;
import com.JJoINT.CamPuzl.domain.Comment.service.PubCommentService;
import com.JJoINT.CamPuzl.global.auth.dto.SecurityMemberDTO;
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

    //pubid 같이 받아오게 추가
    @GetMapping("/{id}")
    public ResponseEntity<Page<PubCommentResponseDTO>> getCommentsByPubId(@PathVariable Long id, Pageable pageable) {
        Page<PubCommentResponseDTO> comments = pubCommentService.getCommentsByPubId(id, pageable)
                .map(pubComment -> new PubCommentResponseDTO(
                        pubComment.getId(),
                        pubComment.getReview(),
                        pubComment.getRating(),
                        pubComment.getWriter()
                ));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<String> saveComment(@PathVariable Long id, @RequestBody PubCommentDTO commentDto, @AuthenticationPrincipal SecurityMemberDTO securityMemberDTO) {
        pubCommentService.saveComment(id, commentDto, securityMemberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        pubCommentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
