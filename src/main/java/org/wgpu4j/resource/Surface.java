package org.wgpu4j.resource;

import org.wgpu4j.WgpuException;
import org.wgpu4j.WgpuNative;
import org.wgpu4j.WgpuResource;
import org.wgpu4j.bindings.WGPUSurfaceTexture;
import org.wgpu4j.bindings.webgpu_h;
import org.wgpu4j.constant.SurfaceTextureStatus;
import org.wgpu4j.descriptor.SurfaceConfiguration;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

/**
 * Represents a surface that can be rendered to (e.g., a window).
 * Surfaces are created from platform-specific handles and configured for rendering.
 */
public class Surface extends WgpuResource {

    static {
        WgpuNative.ensureLoaded();
    }

    protected Surface(MemorySegment handle) {
        super(handle);
    }

    /**
     * Configures the surface with the specified configuration.
     * This sets up the swapchain properties like format, size, and present mode.
     *
     * @param config The surface configuration
     */
    public void configure(SurfaceConfiguration config) {
        checkNotClosed();

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment configStruct = config.marshal(arena);
            webgpu_h.wgpuSurfaceConfigure(handle, configStruct);
        } catch (Exception e) {
            throw new WgpuException("Failed to configure surface", e);
        }
    }

    /**
     * Unconfigures the surface, releasing swapchain resources.
     */
    public void unconfigure() {
        checkNotClosed();

        try {
            webgpu_h.wgpuSurfaceUnconfigure(handle);
        } catch (Exception e) {
            throw new WgpuException("Failed to unconfigure surface", e);
        }
    }

    /**
     * Gets the current texture from the surface for rendering.
     * This texture should be used as a render target and then presented.
     *
     * @return SurfaceTexture containing the current framebuffer texture
     */
    public SurfaceTexture getCurrentTexture() {
        checkNotClosed();

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment surfaceTexture = WGPUSurfaceTexture.allocate(arena);
            webgpu_h.wgpuSurfaceGetCurrentTexture(handle, surfaceTexture);

            MemorySegment textureHandle = WGPUSurfaceTexture.texture(surfaceTexture);
            SurfaceTextureStatus status = SurfaceTextureStatus.fromValue(WGPUSurfaceTexture.status(surfaceTexture));

            if (textureHandle.equals(MemorySegment.NULL)) {
                return new SurfaceTexture(null, status);
            }

            Texture texture = new Texture(textureHandle);

            return new SurfaceTexture(texture, status);
        } catch (Exception e) {
            throw new WgpuException("Failed to get current surface texture", e);
        }
    }

    /**
     * Presents the current surface texture to the screen.
     * This should be called after rendering to the current texture.
     */
    public void present() {
        checkNotClosed();

        try {
            webgpu_h.wgpuSurfacePresent(handle);
        } catch (Exception e) {
            throw new WgpuException("Failed to present surface", e);
        }
    }

    @Override
    protected void releaseNative() {
        try {
            webgpu_h.wgpuSurfaceRelease(handle);
        } catch (Exception e) {
            throw new WgpuException("Failed to release surface", e);
        }
    }

    /**
     * Represents a texture obtained from a surface for rendering.
     */
    public static class SurfaceTexture {
        private final Texture texture;
        private final SurfaceTextureStatus status;

        public SurfaceTexture(Texture texture, SurfaceTextureStatus status) {
            this.texture = texture;
            this.status = status;
        }

        /**
         * Gets the texture for rendering.
         */
        public Texture getTexture() {
            return texture;
        }

        /**
         * Gets the status of obtaining this texture.
         */
        public SurfaceTextureStatus getStatus() {
            return status;
        }

        /**
         * Returns true if the texture was obtained successfully.
         */
        public boolean isSuccess() {
            return status == SurfaceTextureStatus.SUCCESS_OPTIMAL
                    || status == SurfaceTextureStatus.SUCCESS_SUB_OPTIMAL;
        }
    }
}