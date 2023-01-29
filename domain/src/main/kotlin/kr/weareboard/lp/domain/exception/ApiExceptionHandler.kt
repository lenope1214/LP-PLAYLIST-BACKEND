package kr.weareboard.lp.domain.exception

import kr.weareboard.lp.core.storage.exception.StorageException
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanInstantiationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<BadRequestException> {
        val message = ex.message ?: REQUEST_BODY_ERROR
        logger.error("httpMessageNotReadableException - message : $message")
        return ResponseEntity<BadRequestException>(
            BadRequestException(
                message
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(StorageException::class)
    fun storageException(ex: StorageException): ResponseEntity<BadRequestException> {
        val message = ex.message
        logger.error("storageException - message : $message")
        return ResponseEntity<BadRequestException>(
            BadRequestException(message ?: "파일 저장 중 오류가 발생했습니다."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    // MethodArgumentNotValidException - @Valid 검증 실패 시 Catch된다.
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<BadRequestException> {
        val message = e.bindingResult
            .allErrors[0]
            .defaultMessage ?: REQUEST_BODY_ERROR
        logger.error("handleMethodArgumentNotValidException - message : $message")
        return ResponseEntity<BadRequestException>(
            BadRequestException(
                message
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // @Valid 검증 실패 시 Catch
    @ExceptionHandler(InvalidParameterException::class)
    fun handleInvalidParameterException(ex: InvalidParameterException): ResponseEntity<InternalServerException> {
        logger.error("handleInvalidParameterException - message : $ex.message")
        return ResponseEntity<InternalServerException>(
            InternalServerException("서버에서 오류가 발생했습니다. - Invalid Parameter Exception"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun dataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<BadRequestException> {
        val message = ex.rootCause?.message
        logger.error("dataIntegrityViolationException - message : $message")
        return ResponseEntity<BadRequestException>(
            BadRequestException(
                "데이터 제약조건 오류가 발생했습니다."
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<String> {
        logger.error("handleBasicException - message : ${ex.message}")
        ex.printStackTrace()
        return ResponseEntity<String>(
            ex.message, HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(BasicException::class)
    fun handleBasicException(ex: BasicException): ResponseEntity<String> {
        logger.error("handleBasicException - message : ${ex.message}")
        ex.printStackTrace()
        return ResponseEntity<String>(
            ex.message, HttpStatus.resolve(ex.status) ?: HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(ex: IllegalArgumentException): ResponseEntity<InternalServerException> {
        logger.error("illegalArgumentExceptionHandler - message : ${ex.message}")
        ex.printStackTrace()
        return ResponseEntity<InternalServerException>(
            InternalServerException(ex.message ?: "서버에서 오류가 발생했습니다."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(BeanInstantiationException::class)
    fun beanInstantiationExceptionHandler(ex: BeanInstantiationException): ResponseEntity<BadRequestException> {
        logger.error("beanInstantiationExceptionHandler - message : ${ex.message}")
        val message: String = ex.message ?: "Bad request"
        var parsedMessage: String = message
        if(parsedMessage.contains(":")){
            val split = parsedMessage.split(":")
            parsedMessage = split[split.size - 1].trim()
        }

        ex.printStackTrace()
        return ResponseEntity<BadRequestException>(
            BadRequestException(parsedMessage),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<String> {
        logger.error("handle ResponseStatusException - message : $ex.message")
        return ResponseEntity<String>(
            ex.message,
            ex.status,
        )
    }

    // 모든 예외를 핸들링하여 ErrorResponse 형식으로 반환한다.
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<InternalServerException> {
        logger.error("handleException - message : ${ex.message}")
        ex.printStackTrace()
        return ResponseEntity<InternalServerException>(
            InternalServerException(UNKNOWN_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    companion object {
        private val REQUEST_BODY_ERROR: String by lazy { "REQUEST-BODY와 관련된 문제가 발생했습니다." }
        private val UNKNOWN_ERROR: String by lazy { "알 수 없는 에러가 발생했습니다." }
    }
}
