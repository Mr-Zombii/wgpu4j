package org.wgpu4j.resource;

import org.wgpu4j.WgpuException;
import org.wgpu4j.WgpuNative;
import org.wgpu4j.WgpuResource;
import org.wgpu4j.bindings.webgpu_h;
import org.wgpu4j.descriptor.Extent3D;
import org.wgpu4j.descriptor.ImageCopyTexture;
import org.wgpu4j.descriptor.ImageCopyTextureLayout;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Command queue for submitting work to the GPU.
 */
public class Queue extends WgpuResource {

    static {
        WgpuNative.ensureLoaded();
    }

    protected Queue(MemorySegment handle) {
        super(handle);
    }

    /**
     * Submits command buffers to the GPU for execution.
     *
     * @param commandBuffers The command buffers to submit
     */
    public void submit(List<CommandBuffer> commandBuffers) {
        checkNotClosed();

        if (commandBuffers.isEmpty()) {
            return;
        }

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment commandArray = arena.allocate(ValueLayout.ADDRESS, commandBuffers.size());

            for (int i = 0; i < commandBuffers.size(); i++) {
                CommandBuffer commandBuffer = commandBuffers.get(i);
                if (commandBuffer.isClosed()) {
                    throw new WgpuException("Cannot submit closed command buffer at index " + i);
                }
                commandArray.setAtIndex(ValueLayout.ADDRESS, i, commandBuffer.getHandle());
            }

            webgpu_h.wgpuQueueSubmit(handle, commandBuffers.size(), commandArray);

        } catch (Exception e) {
            throw new WgpuException("Failed to submit command buffers", e);
        }
    }

    /**
     * Submits a single command buffer.
     *
     * @param commandBuffer The command buffer to submit
     */
    public void submit(CommandBuffer commandBuffer) {
        submit(List.of(commandBuffer));
    }

    /**
     * Writes data to a buffer on the GPU.
     *
     * @param buffer       The buffer to write to
     * @param bufferOffset The offset in the buffer to start writing at
     * @param data         The data to write
     */
    public void writeBuffer(Buffer buffer, long bufferOffset, byte[] data) {
        writeBuffer(buffer, bufferOffset, MemorySegment.ofArray(data));
    }

    /**
     * Writes data to a buffer on the GPU.
     *
     * @param buffer       The buffer to write to
     * @param bufferOffset The offset in the buffer to start writing at
     * @param data         The data to write
     * @param dataOffset   The offset into the data
     * @param dataSize     The amount of data to write
     */
    public void writeBuffer(Buffer buffer, long bufferOffset, byte[] data, int dataOffset, int dataSize) {
        writeBuffer(buffer, bufferOffset, MemorySegment.ofArray(data).asSlice(dataOffset, dataSize));
    }

    /**
     * Writes data to a buffer on the GPU.
     *
     * @param buffer       The buffer to write to
     * @param bufferOffset The offset in the buffer to start writing at
     * @param data         The data to write
     */
    public void writeBuffer(Buffer buffer, long bufferOffset, MemorySegment data) {
        checkNotClosed();
        if (buffer.isClosed()) {
            throw new WgpuException("Cannot write to closed buffer");
        }

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment dataSegment = arena.allocate(data.byteSize());
            MemorySegment.copy(data, 0, dataSegment, 0, data.byteSize());
            webgpu_h.wgpuQueueWriteBuffer(handle, buffer.getHandle(), bufferOffset, dataSegment, (int)data.byteSize());
        } catch (Exception e) {
            throw new WgpuException("Failed to write buffer", e);
        }
    }

    /**
     * Writes data to a texture on the GPU.
     *
     * @param destination The texture destination descriptor
     * @param data        The texture data to write
     * @param dataLayout  The layout of the data in memory
     * @param writeSize   The size of the region to write
     */
    public void writeTexture(ImageCopyTexture destination, byte[] data, ImageCopyTextureLayout dataLayout, Extent3D writeSize) {
        writeTexture(destination, MemorySegment.ofArray(data), dataLayout, writeSize);
    }

    /**
     * Writes data to a texture on the GPU.
     *
     * @param destination The texture destination descriptor
     * @param data        The texture data to write
     * @param dataOffset  The offset to the texture data
     * @param dataSize    The size of the texture data
     * @param dataLayout  The layout of the data in memory
     * @param writeSize   The size of the region to write
     */
    public void writeTexture(ImageCopyTexture destination, byte[] data, int dataOffset, int dataSize, ImageCopyTextureLayout dataLayout, Extent3D writeSize) {
        writeTexture(destination, MemorySegment.ofArray(data).asSlice(dataOffset, dataSize), dataLayout, writeSize);
    }

    /**
     * Writes data to a texture on the GPU.
     *
     * @param destination The texture destination descriptor
     * @param data        The texture data to write
     * @param dataLayout  The layout of the data in memory
     * @param writeSize   The size of the region to write
     */
    public void writeTexture(ImageCopyTexture destination, MemorySegment data, ImageCopyTextureLayout dataLayout, Extent3D writeSize) {
        checkNotClosed();
        if (destination == null) {
            throw new WgpuException("Destination cannot be null");
        }
        if (data == null || data.byteSize() == 0) {
            throw new WgpuException("Data cannot be null or empty");
        }
        if (dataLayout == null) {
            throw new WgpuException("Data layout cannot be null");
        }
        if (writeSize == null) {
            throw new WgpuException("Write size cannot be null");
        }

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment destinationStruct = destination.marshal(arena);
            MemorySegment dataLayoutStruct = dataLayout.marshal(arena);
            MemorySegment writeSizeStruct = writeSize.marshal(arena);

            MemorySegment dataSegment = arena.allocate(data.byteSize());
            MemorySegment.copy(data, 0, dataSegment, 0, data.byteSize());

            webgpu_h.wgpuQueueWriteTexture(
                    handle,
                    destinationStruct,
                    dataSegment,
                    data.byteSize(),
                    dataLayoutStruct,
                    writeSizeStruct
            );
        } catch (Exception e) {
            throw new WgpuException("Failed to write texture", e);
        }
    }

    @Override
    protected void releaseNative() {
        try {
            webgpu_h.wgpuQueueRelease(handle);
        } catch (Exception e) {
            throw new WgpuException("Failed to release queue", e);
        }
    }
}