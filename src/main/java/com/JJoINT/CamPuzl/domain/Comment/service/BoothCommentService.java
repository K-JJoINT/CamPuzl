package com.JJoINT.CamPuzl.domain.Comment.service;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;

import java.util.ArrayList;

public interface BoothCommentService {

    BoothComment save(Long boothID, BoothCommentDTO boothComment);

    ArrayList<BoothCommentDTO> findByIdAll(Long id);

    void deleteById(Long id);

    BoothComment update(Long boothID, BoothCommentDTO boothCommentDTO);
}
