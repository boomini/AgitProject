package com.ssafy.api.controller;


import com.ssafy.api.dto.ImageDto;
import com.ssafy.api.dto.VideoDto;
import com.ssafy.api.service.ImageService;
import com.ssafy.api.service.VideoService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.time.LocalDate;
import java.util.StringTokenizer;
import java.util.UUID;

@Api(value = "동영상 API", tags = {"Video"})
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @Autowired
    ServletContext servletContext;
    @PostMapping()
    @ApiOperation(value="이미지 업로드", notes="이미지 업로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addImage (
            @RequestParam(value="upfile", required = true) MultipartFile[] files ,@RequestParam(value="uploadDate")String uploadDate,@RequestParam(value="userId")String userId,@RequestParam(value="teamId")Long teamId) throws Exception {


        //System.out.println(servletContext.getRealPath("/resources/dist/video"));
        String realPath = servletContext.getRealPath("/resources/dist/video");
        //String realPath =  System.getProperty("user.dir") + "\\files";
        System.out.println(realPath);
        System.out.println(userId);
        StringTokenizer st = new StringTokenizer(uploadDate,"-");
        String ccurday = "";
        while(st.hasMoreElements()){
            ccurday+=st.nextToken();
        }
        String saveFolder = realPath + File.separator + ccurday;

        File folder = new File(saveFolder);
        if(!folder.exists())
            folder.mkdirs();

        for(MultipartFile mfile : files){
            VideoDto videoDto = new VideoDto();
            String originalFileName = mfile.getOriginalFilename();
            if(!originalFileName.isEmpty()){
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                //유일한 식별자를 생성한다.
                videoDto.setFilePath(ccurday);
                videoDto.setOriginalFileName(originalFileName);
                videoDto.setFileName(saveFileName);
                videoDto.setUploadDate(LocalDate.parse(uploadDate));
                System.out.println("원본 파일 이름 : "+ mfile.getOriginalFilename() + " 실제 저장 파일이름 : " + saveFileName);
                mfile.transferTo(new File(folder, saveFileName));
            }
            videoService.addVideo(videoDto,userId,teamId);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    }
