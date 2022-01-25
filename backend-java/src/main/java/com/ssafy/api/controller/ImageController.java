package com.ssafy.api.controller;

import com.ssafy.api.dto.ArticleDto;
import com.ssafy.api.dto.ImageDto;
import com.ssafy.api.service.ImageService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Article;
import io.swagger.annotations.*;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

@Api(value = "이미지 API", tags = {"Image"})
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    ServletContext servletContext;
    @PostMapping()
    @ApiOperation(value="이미지 업로드", notes="이미지 업로드 api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addImage (
           @RequestParam(value="upfile", required = true) MultipartFile[] files ,@RequestParam(value="uploadDate")String uploadDate,@RequestParam(value="userId")String userId,@RequestParam(value="teamId")Long teamId) throws Exception {


        //System.out.println(servletContext.getRealPath("/resources/dist/img"));
        String realPath = servletContext.getRealPath("/resources/dist/img");
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
            ImageDto imageDto = new ImageDto();
            String originalFileName = mfile.getOriginalFilename();
            if(!originalFileName.isEmpty()){
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                //유일한 식별자를 생성한다.
                imageDto.setFilePath(ccurday);
                imageDto.setOriginalFileName(originalFileName);
                imageDto.setFileName(saveFileName);
                imageDto.setUploadDate(LocalDate.parse(uploadDate));
                System.out.println("원본 파일 이름 : "+ mfile.getOriginalFilename() + " 실제 저장 파일이름 : " + saveFileName);
                mfile.transferTo(new File(folder, saveFileName));
            }
            imageService.addImage(imageDto,userId,teamId);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

}
