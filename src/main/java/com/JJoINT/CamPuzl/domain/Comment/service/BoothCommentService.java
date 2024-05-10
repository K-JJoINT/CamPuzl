package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.responseBoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.dto.requestBoothCommentDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

public interface BoothCommentService {

    BoothComment save(HttpServletRequest request, Long boothId, requestBoothCommentDTO requestDTO);

    ArrayList<responseBoothCommentDTO> findByIdAll(Long boothId);

    BoothComment update(Long boothCommentId, requestBoothCommentDTO requestDTO);

    void deleteById(Long boothCommentId);

    void deletedByIdAll(Long boothId);

}
