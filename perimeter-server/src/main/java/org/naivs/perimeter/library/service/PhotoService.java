package org.naivs.perimeter.library.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.file.FileSystemDirectory;
import org.naivs.perimeter.converter.AbstractConverter;
import org.naivs.perimeter.exception.PhotoServiceException;
import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.naivs.perimeter.smarthome.data.repository.PhotoIndexRepository;
import org.naivs.perimeter.smarthome.data.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Value("${photo.catalog}")
    private String photoBasePath;
    @Value("${base.catalog}")
    private String baseCatalog;
    private static final float THUMBS_SIDE_SIZE = 200.0F;
    private final PhotoRepository photoRepository;
    private final PhotoIndexRepository photoIndexRepository;
    private final AbstractConverter converter;

    private Random random = new Random();

    @Autowired
    public PhotoService(PhotoRepository photoRepository,
                        PhotoIndexRepository photoIndexRepository,
                        AbstractConverter converter) {
        this.photoRepository = photoRepository;
        this.photoIndexRepository = photoIndexRepository;
        this.converter = converter;
    }

    /**
     * Get file of original photo. Uses for obtain photo from storage and receive over http.
     * @param id photo entity identification
     * @return original photo file
     * @throws PhotoServiceException throws if file not registered in database or
     * if file not exists in storage or if not a file
     */
    public File getOriginalFile(Long id) throws PhotoServiceException {
        Photo photo = photoRepository.findPhotoById(id).orElseThrow(() ->
                        new PhotoServiceException(
                                String.format("Photo with id=%d not found", id)));
        File originalPhoto = Paths.get(photoBasePath)
                .resolve(photo.getPath())
                .resolve(photo.getFilename())
                .normalize().toFile();
        if (!originalPhoto.exists() || !originalPhoto.isFile()) {
            throw new PhotoServiceException(String.format("File of original photo: %s not exists or is not a file",
                    photo.getPath() + photo.getFilename()));
        }
        return originalPhoto;
    }

    /**
     * Get all original photo catalogs from specified catalog.
     * @param relativePath path of catalog (relative to photo storage base path)
     * @return List of founded catalogs
     */
    public List<File> getOriginalCatalogs(Path relativePath) throws PhotoServiceException {
        Path base = Paths.get(photoBasePath);
        File catalog = base.resolve(relativePath).toFile();
        if (catalog.exists() && catalog.isDirectory()) {
            File[] catalogs = catalog.listFiles(File::isDirectory);
            return catalogs == null ? Collections.emptyList() : Arrays.asList(catalogs);
        } else {
            throw new PhotoServiceException(String.format("Path %s is not valid", relativePath.toString()));
        }
    }

    /**
     * Get thumbnail image by file name.
     * @param filename file name with extension
     * @return thumbnail image file
     * @throws PhotoServiceException throws if file not exists or is not a file
     */
    public File getThumbnail(String filename) throws PhotoServiceException {
        File thumb = Paths.get(baseCatalog).resolve(filename).toFile();
        if (!thumb.exists() && !thumb.isFile()) {
            throw new PhotoServiceException(String.format("Thumbnail file with name %s not found or it is not a file", filename));
        }
        return thumb;
    }

    /**
     * Get all photos metadata from specified catalog. Catalog specify relative to base storage catalog.
     * Uses for scan photo storage and obtaining metadata.
     * @param path catalog in storage relative to base path
     * @return List of Photo metadata
     */
    public List<Photo> getPhotosMetadataFromStorage(Path path) throws PhotoServiceException {
        List<Photo> photoList = new ArrayList<>();
        Path base = Paths.get(photoBasePath);
        File catalog = base.resolve(path).toFile();
        if (catalog.exists() && catalog.isDirectory()) {
            File[] photos = catalog.listFiles(pathname -> pathname.isFile() &&
                    pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".jpeg")
                    || pathname.getName().endsWith(".png") || pathname.getName().endsWith(".JPG")
                    || pathname.getName().endsWith(".JPEG") || pathname.getName().endsWith(".PNG"));
            if (photos != null) {
                for (File photoFile : photos) {
                    Photo photo = new Photo();
                    photo.setFilename(photoFile.getName());
                    photo.setName(photoFile.getName().substring(0, photoFile.getName().lastIndexOf('.')));
                    Path absolutePath = photoFile.toPath().getParent();
                    photo.setPath(base.getNameCount() == absolutePath.getNameCount() ?
                            "" : absolutePath.subpath(base.getNameCount(), absolutePath.getNameCount()).normalize().toString());
                    photo.setTimestamp(getTimestamp(photoFile));
                    photo.setHash(hash(photoFile));
                    photoList.add(photo);
                }
            }
            return photoList;
        } else {
            throw new PhotoServiceException(String.format("Path %s is not valid", path.toString()));
        }
    }

    /**
     * Get all Photo metadata objects which stored in database in "photo" table
     * @param indexes string index name of Photo
     * @return List of Photo objects
     */
    public List<Photo> getPhotosFromDatabase(String[] indexes) {
        return photoRepository.findByIndexes(indexes);
    }

    public List<Photo> getPhotosFromDatabaseByPath(Path path) {
        return photoRepository.findAllByPath(path.toString());
    }

    /**
     * Get all photo indexes
     * @return List of founded indexes
     */
    public List<PhotoIndex> getIndexes() {
        return photoIndexRepository.findAll();
    }

    /**
     * Get random original photo file from storage. It walks throw all storage catalogs and take random file by
     * java.util.Random.
     * @return original photo file
     */
    public File getRandomPhoto() {
        //todo: walk throw storage is too long. This method must work with database only
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

    public void removePhotoFromPhotobase(Photo photo) throws PhotoServiceException {
        /*
        delete file
        delete thumbnail
        delete database data cascade
         */

        File originalPhoto = Paths.get(photoBasePath).resolve(photo.getPath()).toFile();
        boolean originDeleted = originalPhoto.delete();
        File thumb = Paths.get(baseCatalog).resolve(photo.getThumbnail()).toFile();
        boolean thumbDeleted = thumb.delete();

        if (originDeleted && thumbDeleted) {
            Photo storedPhoto = photoRepository.findPhotoByFilenameAndPath(photo.getFilename(), photo.getPath())
                    .orElseThrow(() -> new PhotoServiceException(
                            String.format("Photo with path: %s and filename: %s not found", photo.getPath(), photo.getFilename())));
            photoRepository.deleteById(storedPhoto.getId());
        }
    }

    /**
     *
     * @param photo filled photo object. All non-null fields must be sets
     * @return saved Photo entity
     */
    public Photo saveToDatabase(Photo photo) {
        if (photo.getName() == null) {
            photo.setName(photo.getFilename().substring(0, photo.getFilename().lastIndexOf('.')));
        }

        photo.getIndexes().addAll(converter.convert(Paths.get(photo.getPath())));

        try {
            createThumb(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<PhotoIndex> findedIndexes = photoIndexRepository
                .findByNameIn(photo.getIndexes().stream().map(PhotoIndex::getName).collect(Collectors.toList()));
        photo.getIndexes().removeIf(index ->
                findedIndexes.stream().anyMatch(findedIndex -> findedIndex.getName().equals(index.getName())));
        photo.getIndexes().addAll(findedIndexes);

        Photo saved = photoRepository.saveAndFlush(photo);
        photo.setId(saved.getId());
        photo.setAdded(saved.getAdded());
        return photo;
    }

    /**
     * Save external photo image. That image saves into file system and metadata saves into database.
     * @param inputStream data
     * @param metadata metadata of photo
     * @return Photo entity
     */
    public Photo saveToPhotobase(InputStream inputStream,
                                 Photo metadata
    ) throws PhotoServiceException {
        Path relativeDir = Paths.get(converter.convert(metadata.getIndexes().stream()
                .map(PhotoIndex::getName).collect(Collectors.toList())))
                .normalize();
        Path photoDirPath = Paths.get(photoBasePath).resolve(relativeDir).normalize();
        File photoDir = photoDirPath.toFile();

        if (!photoDir.exists()) {
            if (!photoDir.mkdirs())
                throw new PhotoServiceException("Can't create directory: \"" + photoBasePath + "\"");
        }

        File photoFile = photoDirPath.resolve(metadata.getFilename()).toFile();

        try {
            if (photoFile.isAbsolute() && !photoFile.exists()) {
                FileCopyUtils.copy(inputStream, Files.newOutputStream(photoFile.toPath(), StandardOpenOption.CREATE_NEW));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new PhotoServiceException("Error on save file into storage. " + e.getMessage());
        }

        Photo photo = new Photo();
        photo.setName(metadata.getName());
        photo.setDescription(metadata.getDescription());
        photo.setFilename(metadata.getFilename());
        photo.setPath(relativeDir.toString());
        LocalDateTime timestamp = getTimestamp(photoFile);
        photo.setTimestamp(timestamp.plusMinutes(1).isAfter(metadata.getTimestamp()) ? metadata.getTimestamp() : timestamp);
        photo.setHash(hash(photoFile));

        return saveToDatabase(photo);
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
                                Photo photo = photoRepository
                                        .findPhotoByFilenameAndPath(file.getFileName().toString(), filePath)
                                        .orElse(new Photo());
                                photo.setName(file.getFileName().toString());
                                photo.setTimestamp(
                                        LocalDateTime.ofInstant(
                                                attributes.creationTime().toInstant(), ZoneId.systemDefault()));
                                photo.setAdded(LocalDateTime.now());
                                photo.setPath(filePath);

                                Path withoutBase = folder.toPath().relativize(file.getParent());
                                if (!withoutBase.toString().isEmpty() && !withoutBase.toString().contains("/")) {
                                    PhotoIndex photoIndex = new PhotoIndex();
                                    photoIndex.setName(withoutBase.toString());
//                                    photoIndex.setPhoto(photo);
                                    photo.getIndexes().add(photoIndex);
                                } else if (withoutBase.toString().contains("/")){
                                    String[] indexNames = withoutBase.toString().split("[/]");
                                    for (String index : indexNames) {
                                        PhotoIndex photoIndex = new PhotoIndex();
                                        photoIndex.setName(index);
//                                        photoIndex.setPhoto(photo);
                                        photo.getIndexes().add(photoIndex);
                                    }
                                }

                                photo.setThumbnail("");
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

    private void createThumb(Photo photo) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(
                Paths.get(photoBasePath).resolve(photo.getPath()).resolve(photo.getFilename()).toFile());

        float height, width;
        if (bufferedImage.getHeight() < bufferedImage.getWidth()) {
            width = THUMBS_SIDE_SIZE;
            float compressP = THUMBS_SIDE_SIZE / bufferedImage.getWidth() * 100;
            height = bufferedImage.getHeight() * compressP / 100;
        } else {
            height = THUMBS_SIDE_SIZE;
            float compressP = THUMBS_SIDE_SIZE / bufferedImage.getHeight() * 100;
            width = bufferedImage.getWidth() * compressP / 100;
        }

        //todo: image scaling must be perform in same size (not relatively to source size)
        BufferedImage output = new BufferedImage((int) width, (int) height, bufferedImage.getType());
        output.createGraphics().drawImage(
                bufferedImage.getScaledInstance(output.getWidth(), output.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(
                output,
                "jpg",
                Paths.get(baseCatalog).resolve(photo.getUuid() + ".jpg").toFile());
        photo.setThumbnail(photo.getUuid() + ".jpg");
    }

    private LocalDateTime getTimestamp(File photoFile) throws PhotoServiceException {
        LocalDateTime date = null;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(photoFile);
            Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (directory != null && directory.getObject(ExifDirectoryBase.TAG_DATETIME_ORIGINAL) != null &&
                    !directory.getObject(ExifDirectoryBase.TAG_DATETIME_ORIGINAL).toString().equals("0000:00:00 00:00:00")) {
                String rawDate  = directory.getObject(ExifDirectoryBase.TAG_DATETIME_ORIGINAL).toString();
                date = LocalDateTime.parse(rawDate, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
            }
            if (date == null) {
                directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                if (directory != null && directory.getObject(ExifDirectoryBase.TAG_DATETIME) != null &&
                        !directory.getObject(ExifDirectoryBase.TAG_DATETIME).toString().equals("0000:00:00 00:00:00")) {
                    String rawDate = directory.getObject(ExifDirectoryBase.TAG_DATETIME).toString();
                    date = LocalDateTime.parse(rawDate, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
                }
            }
            if (date == null) {
                directory = metadata.getFirstDirectoryOfType(FileSystemDirectory.class);
                if (directory != null && directory.getObject(FileSystemDirectory.TAG_FILE_MODIFIED_DATE) != null) {
                    Date rawDate = (Date) directory.getObject(FileSystemDirectory.TAG_FILE_MODIFIED_DATE);
                    date = LocalDateTime.ofInstant(rawDate.toInstant(), ZoneId.systemDefault());
                }
            }
            if (date == null) {
                throw new ImageProcessingException("Date of photo taken not found in Exif data. Trying get from file...");
            }
            return date;
        } catch (IOException | ImageProcessingException e) {
            e.printStackTrace();
            try {
                FileTime creationTime = (FileTime) Files.getAttribute(
                        photoFile.toPath(), "creationTime");
                return LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
            } catch (IOException ex) {
                throw new PhotoServiceException(
                        String.format("Unable to set creation date to file %s", photoFile.getAbsolutePath()));
            }
        }
    }

    private String hash(File file) {
        try (InputStream in = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] block = new byte[4096];
            int length;
            while ((length = in.read(block)) > 0) {
                digest.update(block, 0, length);
            }
            return byteToHexString(digest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String byteToHexString(byte[] hash) {
        StringBuilder result = new StringBuilder();
        for (byte hash1 : hash) {
            result.append(Integer.toString((hash1 & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
