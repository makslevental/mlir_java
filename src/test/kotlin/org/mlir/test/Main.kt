package org.mlir.test

import org.mlir.bindings.Bindings
import org.mlir.bindings.MlirStringRef
import java.lang.foreign.Arena

fun main() {
    val modStr = "module {}"
    Arena.ofConfined().use { arena ->
        val modStrSegment = arena.allocateFrom(modStr)
        val stringRefStruct = arena.allocate(MlirStringRef.layout())
        MlirStringRef.length(stringRefStruct, modStr.codePoints().count())
        MlirStringRef.data(stringRefStruct, modStrSegment)

        val ctx = Bindings.mlirContextCreate(arena)
        val mod = Bindings.mlirModuleCreateParse(arena, ctx, stringRefStruct)
        val op = Bindings.mlirModuleGetOperation(arena, mod)
        Bindings.mlirOperationDump(op)
    }
}
