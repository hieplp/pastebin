package dev.hieplp.pastebin.adapter.out.s3.adapter;

import dev.hieplp.pastebin.adapter.out.s3.config.S3StorageProperties;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.ContentType;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3StorageAdapterTest {

    @Mock
    private S3Client s3;

    private S3StorageAdapter adapter;

    @BeforeEach
    void setUp() {
        var props = new S3StorageProperties("pastebin", "us-east-1", "k", "s", "http://localhost:9000", true);
        adapter = new S3StorageAdapter(s3, props);
    }

    @Test
    void uploadReadDelete_usesS3Client() {
        var file = new PasteFile();
        file.setStorageKey(StorageKey.of("abc"));
        file.setContentType(ContentType.of("text/plain"));
        file.setContent("hi".getBytes());

        assertEquals("abc", adapter.upload(file).value());
        verify(s3).putObject(any(PutObjectRequest.class), any(RequestBody.class));

        when(s3.getObjectAsBytes(any(GetObjectRequest.class)))
                .thenReturn(ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), "hi".getBytes()));
        assertArrayEquals("hi".getBytes(), adapter.read(StorageKey.of("abc")));

        adapter.delete(StorageKey.of("abc"));
        adapter.delete(null);
        adapter.delete(StorageKey.of(""));
        verify(s3, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    void read_missingKey_throwsBadRequest() {
        when(s3.getObjectAsBytes(any(GetObjectRequest.class)))
                .thenThrow(NoSuchKeyException.builder().message("missing").build());

        assertThrows(BadRequestException.class, () -> adapter.read(StorageKey.of("missing")));
    }

    @Test
    void listKeys_returnsKeys() {
        stubListedObjects(
                S3Object.builder().key("a").build(),
                S3Object.builder().key("b").build());

        assertEquals(List.of(StorageKey.of("a"), StorageKey.of("b")), adapter.listKeys());
    }

    @Test
    void listKeys_emptyBucket_returnsEmpty() {
        stubListedObjects();

        assertEquals(List.of(), adapter.listKeys());
    }

    @SuppressWarnings("unchecked")
    private void stubListedObjects(S3Object... objects) {
        var paginator = mock(ListObjectsV2Iterable.class);
        when(paginator.contents()).thenReturn(() -> List.of(objects).iterator());
        when(s3.listObjectsV2Paginator(any(Consumer.class))).thenReturn(paginator);
    }
}
