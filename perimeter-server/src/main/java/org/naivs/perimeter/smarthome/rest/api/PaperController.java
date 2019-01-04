package org.naivs.perimeter.smarthome.rest.api;

import org.apache.commons.io.IOUtils;
import org.naivs.perimeter.library.data.PaperEntity;
import org.naivs.perimeter.library.service.PaperService;
import org.naivs.perimeter.smarthome.rest.to.PaperTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/papers")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Value("${library.store}")
    private String storePath;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public void addPaper(MultipartHttpServletRequest request, HttpServletResponse response) {
        MultiValueMap<String, MultipartFile> files = request.getMultiFileMap();
        Map<String, String[]> params = request.getParameterMap();
        PaperEntity paper = new PaperEntity();

        try {
            // from frontend
            paper.setName(params.get("name")[0]);
            paper.setAuthorId(Long.parseLong(params.get("authorId")[0]));
            paper.setYear(Integer.parseInt(params.get("year")[0]));
            paper.setLanguage(params.get("language")[0]);
            paper.setGenre(params.get("genre")[0]);
            paper.setType(params.get("type")[0]);
            paper.setLocation(params.get("location")[0]);
            paper.setDescription(params.get("description")[0]);

            // auto
            MultipartFile cover = files.getFirst("coverFile");
            if (cover != null) {
                paper.setCoverPath(convert(cover, storePath).getName());
            }
            File paperFile = convert(files.getFirst("paperFile"), storePath);
            paper.setFilePath(paperFile.getName());
            paper.setLoadDate(new Date(new java.util.Date().getTime()));
            int dotIndex = paper.getFilePath().lastIndexOf(".");
            paper.setFormat(paper.getFilePath().substring(dotIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }

        paperService.add(paper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<PaperTo> getPapers(HttpServletRequest request) {
        return paperService.getAll().stream().map(paper -> {
            PaperTo paperTo = new PaperTo();
            paperTo.setId(paper.getId());
            paperTo.setName(paper.getName());
            paperTo.setDescription(paper.getDescription());
            paperTo.setYear(paper.getYear());
            paperTo.setAuthorId(paper.getAuthorId());
            paperTo.setLanguage(paper.getLanguage());
            paperTo.setGenre(paper.getGenre());
            paperTo.setType(paper.getType());
            paperTo.setLocation(paper.getLocation());

            paperTo.setFileUrl(request.getRequestURI() + "/paper/" + paper.getFilePath());
            if (paper.getCoverPath() != null && !paper.getCoverPath().isEmpty()) {
                paperTo.setCoverUrl(
                        request.getRequestURI() + "/cover/" + paper.getCoverPath());
            }

            paperTo.setLoadDate(paper.getLoadDate());
            paperTo.setFormat(paper.getFormat());
            return paperTo;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/cover/{coverFileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCover(@PathVariable("coverFileName") String coverFileName) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(
                new File(storePath + coverFileName)))) {
            byte[] image = IOUtils.toByteArray(in);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new byte[0]);
    }

    @RequestMapping(value = "/paper/{paperFileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPaperFile(@PathVariable("paperFileName") String paperFileName) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(
                new File(storePath + paperFileName)))) {
            byte[] image = IOUtils.toByteArray(in);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new byte[0]);
    }

//    private File multipartToFile(MultipartFile multipart, String path) throws IllegalStateException, IOException
//    {
//        File convFile = new File(path + multipart.getOriginalFilename());
//        multipart.transferTo(convFile);
//        return convFile;
//    }

    /**
     * @param file - MultipartFile from frontend
     * @param path - Absolute path directory (store)
     * @return Created file
     * @throws IOException - throws if trouble with file creation on disk
     */
    private File convert(MultipartFile file, String path) throws IOException {
        File convFile = new File(path + file.getOriginalFilename());
        if (convFile.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } else {
            throw new IOException(String.format("Unable to create file %s", convFile.getAbsolutePath()));
        }
    }
}
