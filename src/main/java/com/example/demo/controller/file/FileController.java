package com.example.demo.controller.file;

import com.example.demo.dto.Item;
import com.example.demo.dto.ItemForm;
import com.example.demo.dto.UploadFile;
import com.example.demo.util.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.session.FileStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequestMapping("/file")
@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {

    @Value("${file.dir}")
    private String fileDir;
    private final FileUtil fileUtil;


    //spring.servlet.multipart.enabled 옵션이 켜져있으면 DispathcerServlet에서 멀티파트 리졸버를 실행한다.
    //멀티파트 리졸버는 멀티파트 요청인 경우 HttpServletRquest를 MultipartHttpServletRequest로 변환한다.
    //스프링 기본 multipartResover는 StandardMultipartHttpServletRequest
    @PostMapping("/upload")
    public String saveFile1(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        Collection<Part> parts = request.getParts();

        return "upload-form";
    }

    @PostMapping("/upload2")
    public String saveFile2(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        Collection<Part> parts = request.getParts();

        for (Part part : parts) {
            log.info("name {}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();

            for (String headerName : headerNames) {
                System.out.println("headerName = " + headerName);
            }

            log.info("submittedFileName = {}", part.getSubmittedFileName());
            log.info("size = {}", part.getSize());

            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("경로 : {]", fullPath);
                part.write(fullPath);
            }

        }


        return "upload-form";
    }


    @PostMapping("/upload3")
    public String saveFile3(@RequestParam String itemName,
                            @RequestParam MultipartFile file,
                            HttpServletRequest request) throws IOException {

        if (!file.isEmpty()) {
            String fullPath = fileDir = file.getOriginalFilename();
            file.transferTo(new File(fullPath));
        }

        return "ok";

    }

    @PostMapping("/upload4")
    public String saveItem(@ModelAttribute ItemForm form,
                           RedirectAttributes redirectedAttributes) throws IOException {
        MultipartFile attachFile = form.getAttachFile();
        UploadFile uploadFile = fileUtil.storeFile(attachFile);

        List<UploadFile> storeImageFiles = fileUtil.storeFiles(form.getImageFiles());
        ;

        Item item = new Item();
        redirectedAttributes.addAttribute("itemId", item);

        return "redirect:/items";

    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileUtil.getFullPath(fileName));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        //Item item = itemRepository.findById(itemId);

        Item item = new Item();
        String storeFilename = item.getAttachFile().getStoredFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource urlResource = new UrlResource("file:" + fileUtil.getFullPath(storeFilename));
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                        .body(urlResource);
    }
}
