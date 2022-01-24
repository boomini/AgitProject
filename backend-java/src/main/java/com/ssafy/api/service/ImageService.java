package com.ssafy.api.service;

import com.ssafy.api.dto.ImageDto;
import com.ssafy.db.entity.Image;

public interface ImageService {
    Image addImage(ImageDto imageDto);

}
