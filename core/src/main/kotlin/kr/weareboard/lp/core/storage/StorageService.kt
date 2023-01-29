package kr.weareboard.lp.core.storage

import kr.weareboard.lp.core.storage.exception.StorageException
import kr.weareboard.lp.core.storage.exception.StorageFileNotFoundException
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Collectors
import javax.transaction.NotSupportedException

@Service
class StorageService {

    @Value("\${storage.prefix}")
    private val prefix: String? = null

    @Value("\${storage.location}")
    private val storageLocation: String? = null

    private val rootLocation: Path
        get() = Paths.get(storageLocation)

    private val EXTENSIONS = arrayOf(
        "JPG", "PNG", "JPEG", "AVI", "WMV", "MP4"
    )
    private val logger = LoggerFactory.getLogger(javaClass)


    fun restore(beforeFilePath: String?, newFile: MultipartFile, filePath: String?): String? {
        // 기존 파일 제거 로직
        beforeFilePath?.let { deleteFile(it) }
        return store(newFile, filePath)
    }


    fun store(file: MultipartFile, filePath: String?): String? {
        val fileName = file.resource.filename!!.replace(" ", "_")
        return store(file, filePath, fileName)
    }

    fun store(file: MultipartFile, filePath: String?, fileName: String): String? {
        val destinationFile: Path
        val filePaths = filePath?.split("/".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val fileExtension: String
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }

            // fileExtension은 mp4  jpg 만 남기고 다 자른다.

            // org.apache.commons.io    -   commons-io-2.5.jar 받으면 가능
            fileExtension = FilenameUtils.getExtension(file.originalFilename)
            // 확장자가 지정된 확장자 모두에 맞지 않는다면 확장자 에러를 반환.
            if (Arrays.stream(EXTENSIONS).noneMatch { ext: String ->
                    ext.uppercase(
                        Locale.getDefault()
                    ) == fileExtension.uppercase(Locale.getDefault())
                }) throw NotSupportedException("파일 확장자를 맞춰주세요")
            val path = pathValidation(*filePaths!!)

            // 상위 디렉토리 판별기..
            destinationFile = Path.of("$path/$fileName")
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        } catch (e: NotSupportedException) {
            var exts = ""
            for (ext in EXTENSIONS) {
                exts = "$exts$ext,"
            }
            throw StorageException("Not supported file extension! available extensions : $exts")
        }
        return "$prefix/$filePath/$fileName"
//        return destinationFile.toString().replace("\\", "/")
    }

    @Throws(IOException::class)
    fun pathValidation(vararg paths: String): String {
        logger.info("rootLocation : $rootLocation")
        var filePath = rootLocation.toString()
        var dirFile = File(filePath)
        // 루트 폴더가 존재하는지 확인 및 없으면 생성.
        if (!dirFile.exists()) Files.createDirectory(dirFile.toPath())

        // 하위 경로로 들어갈때, 해당 폴더가 없으면 오류가 발생함.
        // 이를 해결하기 위해 폴더 체크 및 생성
//        if (paths != null) {
            for (i in paths.indices) {
                filePath = filePath + "/" + paths[i]
                dirFile = File(filePath)
                if (!dirFile.exists()) Files.createDirectory(dirFile.toPath())
            }
//        }
        // 전체 경로를 반환한다.
        return filePath
    }

    fun loadAll(): List<Path>? {
        return try {
            //            System.out.println("loadAll()'s this.rootLocation : " + this.rootLocation);
            Files.walk(rootLocation, 1)
                .filter { path: Path -> path != rootLocation }
                .map { other: Path? ->
                    rootLocation.relativize(
                        other
                    )
                }
                .collect(
                    Collectors.toList()
                )
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    fun load(filename: String?, path: String?): Path {
//        System.out.println("load -> path : " + path);
        return Path.of(path).resolve(filename)
    }

    fun deleteFile(filePath: String?) {
        try {
            val file = File(filePath)
            // 파일이 있다면,
            if (file.exists()) {
                // 해당 파일을 지운다.
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    fun init() {
        try {
            Files.createDirectories(rootLocation)
            //            Files.createDirectories(Path.of(rootLocation + "\\menu"));
            Files.createDirectories(Path.of(rootLocation.toString() + "\\shop"))
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    fun loadFile(vararg fullPath: String): Resource? {
        var path = ""
        return try {
            for (i in 0 until fullPath.size - 1) {
                path += fullPath[i] + "/"
            }
            //            System.out.println("loadImg -1 : " + fullPath[fullPath.length-1]);
            val file = load(fullPath[fullPath.size - 1], path)
            val resource: Resource = UrlResource(file.toUri())
            //            System.out.println("loadAsResource's file.toUri() : " + file.toUri());
            //            System.out.println("Resource is readable ? " + resource.isReadable());
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file!! fileName : " + file.fileName
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file!!")
        }
    }

    fun getFilenameParsePath(imgPath: String): String? {
//        System.out.println("getFilenameParsePath's return filename : " + imgPath.split("/")[imgPath.split("/").length-1]);
        return imgPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[imgPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1]
    }

    fun getPathParsePath(imgPath: String): String? {
        var path = ""
        val paths = imgPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in 0 until paths.size - 1) {
            path += paths[i]
        }
        //        System.out.println("getPathParsePath's return path : " + path);
        return path
    }
}