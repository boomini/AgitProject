package com.ssafy.api.service;

import com.ssafy.api.dto.ImageDto;
import com.ssafy.db.entity.Image;

import java.util.List;

public interface ImageService {
    Image addImage(ImageDto imageDto,String userId, Long teamId);
    List<ImageDto> getImageListById(Long teamId);

    List<ImageDto> getImageListAtDateByTeamId(String uploadDate, Long teamId);
}
