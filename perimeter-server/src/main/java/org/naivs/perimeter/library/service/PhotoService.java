package org.naivs.perimeter.library.service;

import org.naivs.perimeter.smarthome.data.entity.PhotoEntity;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.naivs.perimeter.smarthome.data.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

@Service
public class PhotoService {

    @Value("${photo.catalog}")
    private String photoBasePath;
    private final PhotoRepository photoRepository;

    private Random random = new Random();

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public void addPhoto(PhotoEntity photo) {
        photoRepository.saveAndFlush(photo);
    }

    public File getRandomPhoto() {
        File root = new File(photoBasePath);
        List<File> fileList = new ArrayList<>();

        try {
            if (root.exists() && root.isDirectory()) {
                Files.walk(root.toPath()).filter(Files::isRegularFile).forEach(path -> fileList.add(path.toFile()));
            } else {
                throw new RuntimeException("Error. " + photoBasePath + " path is not exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList.get(random.nextInt(fileList.size()));
    }

    public void scanAndPersist() {
        File folder = new File(photoBasePath);

        try {
            if (folder.exists() && folder.isDirectory()) {
                Files.walk(folder.toPath()).filter(path -> {
                            if (!Files.isDirectory(path)) {
                                String name = path.toString();
                                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")
                                        || name.endsWith(".JPG") || name.endsWith(".JPEG") || name.endsWith(".PNG");
                            } else {
                                return false;
                            }
                        }).forEach(file -> {
                            try {
                                String filePath = file.toAbsolutePath().toString();
                                BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
                                PhotoEntity photo = photoRepository
                                        .findPhotoEntityByNameAndPath(file.getFileName().toString(), filePath)
                                        .orElse(new PhotoEntity());
                                photo.setName(file.getFileName().toString());
                                photo.setTimestamp(
                                        LocalDateTime.ofInstant(
                                                attributes.creationTime().toInstant(), ZoneId.systemDefault()));
                                photo.setAdded(LocalDateTime.now());
                                photo.setPath(filePath); // todo: path without filename

                                Path withoutBase = folder.toPath().relativize(file.getParent());
                                if (!withoutBase.toString().isEmpty() && !withoutBase.toString().contains("/")) {
                                    PhotoIndex photoIndex = new PhotoIndex();
                                    photoIndex.setName(withoutBase.toString());
                                    photo.getIndexes().add(photoIndex);
                                } else if (withoutBase.toString().contains("/")){
                                    String[] indexNames = withoutBase.toString().split("[/]");
                                    for (String index : indexNames) {
                                        PhotoIndex photoIndex = new PhotoIndex();
                                        photoIndex.setName(index);
                                        photo.getIndexes().add(photoIndex);
                                    }
                                }

                                photoRepository.saveAndFlush(photo);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void walk(File file) {
        if (!file.exists()) {
            throw new RuntimeException("File " + file.getAbsolutePath() + " is not exists");
        }

        if (file.isDirectory()) {
            // persist directory
            System.out.println("Directory: " + file.getAbsolutePath());
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }

            for (File f : files) {
                walk(f);
            }
        } else {
            // process file
            System.out.println("File: " + file.getName());
        }
    }

    private void createThumb() throws IOException {
        File file = new File("1.png");
        System.out.println(file.getAbsolutePath());
        BufferedImage bufferedImage = ImageIO.read(file);
        int width = bufferedImage.getWidth(),
                height = bufferedImage.getHeight();

        BufferedImage output = new BufferedImage(width / 6, height / 6, bufferedImage.getType());
        output.createGraphics().drawImage(
                bufferedImage.getScaledInstance(output.getWidth(), output.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(
                output,
                file.getName().split("[.]")[1],
                new File(file.getName().split("[.]")[0] + "_thumb." + file.getName().split("[.]")[1]));
    }

    public void addCatalog(Path path) {
        File catalog = path.toFile();
        if (catalog.exists()) {
            throw new RuntimeException("Catalog " + catalog.getName() + " already exists");
        }

        if (catalog.mkdir()) {
            System.out.println("Catalod " + catalog.getName() + " was created");
        } else {
            throw new RuntimeException("Unable to create catalog " + catalog.getName());
        }
    }
}
