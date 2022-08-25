package ari.paran.service.store;

import ari.paran.domain.board.Article;
import ari.paran.domain.board.ArticleImgFile;
import ari.paran.domain.repository.ArticleImgFilesRepository;
import ari.paran.domain.repository.BoardRepository;
import ari.paran.domain.repository.StoreImgFileRepository;
import ari.paran.domain.repository.StoreRepository;
import ari.paran.domain.store.StoreImgFile;
import ari.paran.domain.store.Store;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService {

    @Autowired StoreRepository storeRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired ArticleImgFilesRepository articleImgFilesRepository;
    @Autowired StoreImgFileRepository storeImgFileRepository;

    @Value("${file.storage.path}")
    private String fileUrl; //"C://Users//김우진//Desktop//파란학기//프로젝트//backend//paran//src//main//resources//images/";

    public void saveImage(Long store_id, List<MultipartFile> images) throws IOException{

        List<StoreImgFile> imgFiles = new ArrayList<>();
        Store store = storeRepository.findById(store_id).orElseGet(null);

        for(MultipartFile image : images) {
            String originalFileName = image.getOriginalFilename();
            String fileName = UUID.randomUUID().toString(); //uuid
            File destinationFile = new File(fileUrl + fileName);

            destinationFile.getParentFile().mkdirs();
            image.transferTo(destinationFile);

            StoreImgFile storeImgFile = StoreImgFile.builder()
                            .store(store)
                            .originalFileName(originalFileName)
                            .filename(fileName)
                            .fileUrl(fileUrl).build();

            store.addImgFile(storeImgFile);

            storeImgFileRepository.save(storeImgFile);
            store.addImgFile(storeImgFile);
        }

        storeRepository.save(store);
    }

    public void saveArticleImage(Long articleId, List<MultipartFile> images) throws IOException{

        String fileUrl = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\";
        Article article = boardRepository.findById(articleId).orElse(null);

        for(MultipartFile image : images) {
            String fileName = image.getOriginalFilename();
            //String extension = StringUtils.getFilenameExtension(fileName).toLowerCase();
            File destinationFile = new File(fileUrl + fileName);

            destinationFile.getParentFile().mkdirs();
            image.transferTo(destinationFile);

            ArticleImgFile articleImgFile = ArticleImgFile.builder()
                    .article(article)
                    .filename(fileName)
                    .fileUrl(fileUrl).build();

            article.addImgFile(articleImgFile);
            articleImgFilesRepository.save(articleImgFile);
        }
    }

    public List<String> getImage(Store store) throws IOException{
        List<String> base64Images = new ArrayList<>();
        List<StoreImgFile> storeImages = store.getStoreImgFiles();

        for(StoreImgFile storeImgFile : storeImages) {
            InputStream in = getClass().getResourceAsStream("/images/" + storeImgFile.getFilename());

            byte[] byteEnc64 = Base64.encodeBase64(in.readAllBytes());
            String imgStr = new String(byteEnc64, "UTF-8");

            base64Images.add(imgStr);
        }

        return base64Images;
    }
}
