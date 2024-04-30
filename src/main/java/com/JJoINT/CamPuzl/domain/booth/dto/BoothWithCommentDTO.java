package com.JJoINT.CamPuzl.domain.booth.dto;

import com.JJoINT.CamPuzl.domain.Comment.domain.BoothComment;
import com.JJoINT.CamPuzl.domain.Comment.dto.BoothCommentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoothWithCommentDTO {
    //커밋용 주석
    private BoothDTO boothDTO;
    private ArrayList<BoothCommentDTO> boothCommentDTO;
}
