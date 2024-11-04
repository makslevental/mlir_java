# Java MLIR Bindings

## Run/test

1. Get a build of LLVM with MLIR enabled from somewhere (e.g., https://github.com/makslevental/mlir-wheels);
2. Download Jextract from https://jdk.java.net/jextract;
3. Set the vars in [scripts/jextract.sh](scripts/jextract.sh) and run;
4. Set `LD_LIBRARY_PATH=$LLVM_INSTALL_DIR/lib`;
5. Run [mlir/test/Main.java](src/test/java/org/mlir/test/Main.java);
6. You should see
   ```mlir
   module {
   }
   ```

Note, you're going to need JDK>=23, and you're going to need to pass

```
--enable-preview --enable-native-access=ALL-UNNAMED
```

as a VM option.