package org.mlir.test;

import org.mlir.bindings.Bindings;
import org.mlir.bindings.MlirStringRef;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class Main {
    public static void main(String[] args) {
        var modStr = "module {}";
        try (Arena arena = Arena.ofConfined()) {
            var modStrSegment = arena.allocateFrom(modStr);
            MemorySegment stringRefStruct = arena.allocate(MlirStringRef.layout());
            MlirStringRef.length(stringRefStruct, modStr.codePoints().count());
            MlirStringRef.data(stringRefStruct, modStrSegment);
            var ctx = Bindings.mlirContextCreate(arena);
            var mod = Bindings.mlirModuleCreateParse(arena, ctx, stringRefStruct);
            var op = Bindings.mlirModuleGetOperation(arena, mod);
            Bindings.mlirOperationDump(op);
        }
    }
}
