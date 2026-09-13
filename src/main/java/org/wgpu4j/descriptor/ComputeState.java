package org.wgpu4j.descriptor;

import org.wgpu4j.Marshalable;
import org.wgpu4j.bindings.WGPUComputeState;
import org.wgpu4j.bindings.WGPUConstantEntry;
import org.wgpu4j.bindings.WGPUStringView;
import org.wgpu4j.resource.ShaderModule;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Configuration for a compute shader stage (vertex, fragment, or compute).
 * Contains the shader module, entry point function name, and shader constants.
 */
public class ComputeState implements Marshalable {
    private final ShaderModule module;
    private final String entryPoint;
    private final List<ConstantEntry> constants;

    private ComputeState(ShaderModule module, String entryPoint, List<ConstantEntry> constants) {
        this.module = module;
        this.entryPoint = entryPoint;
        this.constants = new ArrayList<>(constants);
    }

    public ShaderModule getModule() {
        return module;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public List<ConstantEntry> getConstants() {
        return new ArrayList<>(constants);
    }

    /**
     * Converts this descriptor to a C struct using jextract layouts.
     *
     * @param arena The arena to allocate the struct in
     * @return MemorySegment representing the WGPUComputeState struct
     */
    public MemorySegment marshal(Arena arena) {
        MemorySegment struct = WGPUComputeState.allocate(arena);

        WGPUComputeState.nextInChain(struct, MemorySegment.NULL);

        WGPUComputeState.module(struct, module.getHandle());

        if (entryPoint != null && !entryPoint.isEmpty()) {
            MemorySegment entryPointBytes = arena.allocateFrom(entryPoint, StandardCharsets.UTF_8);
            MemorySegment entryPointStringView = WGPUComputeState.entryPoint(struct);
            WGPUStringView.data(entryPointStringView, entryPointBytes);
            WGPUStringView.length(entryPointStringView, entryPoint.length());
        } else {
            MemorySegment entryPointStringView = WGPUComputeState.entryPoint(struct);
            WGPUStringView.data(entryPointStringView, MemorySegment.NULL);
            WGPUStringView.length(entryPointStringView, 0);
        }

        WGPUComputeState.constantCount(struct, constants.size());
        if (!constants.isEmpty()) {
            MemorySegment constantsArray = WGPUConstantEntry.allocateArray(constants.size(), arena);
            for (int i = 0; i < constants.size(); i++) {
                MemorySegment constantStruct = WGPUConstantEntry.asSlice(constantsArray, i);
                MemorySegment.copy(constants.get(i).marshal(arena), 0L, constantStruct, 0L, WGPUConstantEntry.sizeof());
            }
            WGPUComputeState.constants(struct, constantsArray);
        } else {
            WGPUComputeState.constants(struct, MemorySegment.NULL);
        }

        return struct;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ShaderModule module;
        private String entryPoint = "main";
        private final List<ConstantEntry> constants = new ArrayList<>();

        public Builder module(ShaderModule module) {
            this.module = module;
            return this;
        }

        public Builder entryPoint(String entryPoint) {
            this.entryPoint = entryPoint;
            return this;
        }

        public Builder constant(String name, double value) {
            this.constants.add(new ConstantEntry(name, value));
            return this;
        }

        public Builder constants(Map<String, Double> constants) {
            this.constants.clear();
            for (Map.Entry<String, Double> entry : constants.entrySet()) {
                this.constants.add(new ConstantEntry(entry.getKey(), entry.getValue()));
            }
            return this;
        }

        public Builder constants(List<ConstantEntry> constants) {
            this.constants.clear();
            this.constants.addAll(constants);
            return this;
        }

        public ComputeState build() {
            if (module == null) {
                throw new IllegalArgumentException("Shader module is required");
            }
            if (entryPoint == null || entryPoint.isEmpty()) {
                throw new IllegalArgumentException("Entry point is required");
            }
            return new ComputeState(module, entryPoint, constants);
        }
    }
}