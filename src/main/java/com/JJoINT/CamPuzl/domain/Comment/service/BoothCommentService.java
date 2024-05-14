package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.ResponseBoothCommentDTO;
import com.JJoINT.CamPuzl.domain.Comment.dto.RequestBoothCommentDTO;

import java.util.ArrayList;

public interface BoothCommentService {

    BoothComment save(Long boothId, RequestBoothCommentDTO requestDTO);

    ArrayList<ResponseBoothCommentDTO> findByIdAll(Long boothId);

    BoothComment update(Long boothCommentId, RequestBoothCommentDTO requestDTO);

    void deleteById(Long boothCommentId);

    void deletedByIdAll(Long boothId);

}
